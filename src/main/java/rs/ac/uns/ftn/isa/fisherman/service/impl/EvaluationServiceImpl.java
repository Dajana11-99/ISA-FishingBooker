package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.mail.EvaluationSuccesfullInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.EvaluationUnapprovedInfo;
import rs.ac.uns.ftn.isa.fisherman.mail.MailService;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.model.Evaluation;
import rs.ac.uns.ftn.isa.fisherman.repository.EvaluationRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;
import javax.mail.MessagingException;
import java.util.List;
@Service
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger logger= LoggerFactory.getLogger(EvaluationServiceImpl.class);
    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private CabinEvaluationService cabinEvaluationService;
    @Autowired
    private BoatEvaluationService boatEvaluationService;
    @Autowired
    private CabinService cabinService;
    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;
    @Override
    public List<Evaluation> getAll() {
        return evaluationRepository.getAll();
    }

    @Override
    public void setEvaluationStatus(Long id) {
     Evaluation evaluation=  evaluationRepository.getById(id);
     evaluation.setApproved(true);
     evaluationRepository.save(evaluation);
        if(evaluation.getType().equals("CABIN EVALUATION")){
          Cabin cabin= cabinEvaluationService.getById(evaluation.getId()).getCabin();
          cabinService.updateCabinGrade(cabin.getId());
            sendMailNotificationForCabinAndBoat(evaluation,cabin.getName(),"cabin",false);
        }else if(evaluation.getType().equals("BOAT EVALUATION")){
            Boat boat= boatEvaluationService.findById(evaluation.getId()).getBoat();
            cabinService.updateCabinGrade(boat.getId());
            sendMailNotificationForCabinAndBoat(evaluation,boat.getName(),"boat",false);
        }else {
            userService.updateOwnersRating(evaluation.getOwnersUsername(),evaluation.getGrade());
            sendMailNotificationForOwners(evaluation,false);
        }

    }

    @Override
    public void deleteUnapprovedEvaluation(Long id) {
        Evaluation evaluation=  evaluationRepository.getById(id);
        if(evaluation.getType().equals("CABIN EVALUATION")){
            Cabin cabin= cabinEvaluationService.getById(evaluation.getId()).getCabin();
            sendMailNotificationForCabinAndBoat(evaluation,cabin.getName(),"cabin",true);
        }else if(evaluation.getType().equals("BOAT EVALUATION")){
            Boat boat= boatEvaluationService.findById(evaluation.getId()).getBoat();
            sendMailNotificationForCabinAndBoat(evaluation,boat.getName(),"boat",true);
        }else {
            userService.updateOwnersRating(evaluation.getOwnersUsername(),evaluation.getGrade());
            sendMailNotificationForOwners(evaluation,true);
        }
        evaluationRepository.deleteById(id);
    }

    @Override
    public List<Evaluation> findCabinOwnerEvaluations(Long id) {
        return evaluationRepository.findCabinOwnerEvaluations(id);
    }

    @Override
    public List<Evaluation> findBoatOwnerEvaluations(Long id) {
        return evaluationRepository.findBoatOwnerEvaluations(id);
    }

    @Override
    public List<Evaluation> findInstructorEvaluations(Long id) {
        return evaluationRepository.findInstructorEvaluations(id);
    }

    @Override
    public List<Evaluation> getBoatEvaluations(Long boatId) {
        return evaluationRepository.findBoatEvaluations(boatId);
    }

    private void sendMailNotificationForCabinAndBoat(Evaluation evaluation,String name,String type,boolean delete){
        try {
            String message = "Your "+ type+" "+ name+" is rated: " + evaluation.getGrade() + " by client: " + evaluation.getClient().getUsername()
                    + ". \n"+
                    "Client comment: "+evaluation.getComment()+".";
                if(delete)
                    mailService.sendMail(evaluation.getOwnersUsername(), message, new EvaluationUnapprovedInfo());
                mailService.sendMail(evaluation.getOwnersUsername(), message, new EvaluationSuccesfullInfo());

        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

    private void sendMailNotificationForOwners(Evaluation evaluation,boolean delete){
        try {
            String message = "Client " +evaluation.getClient().getUsername()+" has rated you " + evaluation.getGrade()
                    + ". "+" Client comment: "+evaluation.getComment()+".";


            if(delete)
                mailService.sendMail(evaluation.getOwnersUsername(), message, new EvaluationUnapprovedInfo());
            mailService.sendMail(evaluation.getOwnersUsername(), message, new EvaluationSuccesfullInfo());

        } catch (MessagingException e) {
            logger.error(e.toString());
        }
    }

}
