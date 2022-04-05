package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AdventureReservation;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationAdventure;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationAdventureRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AdventureReservationService;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableInstructorPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationAdventureService;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class QuickReservationAdventureImpl implements QuickReservationAdventureService {
    @Autowired
    private QuickReservationAdventureRepository quickReservationAdventureRepository;
    @Autowired
    private AvailableInstructorPeriodService availableInstructorPeriodService;
    @Autowired
    private AdventureReservationService adventureReservationService;
    @Override
    public boolean instructorCreates(QuickReservationAdventure quickReservationAdventure) {
        if(!validateForReservation(quickReservationAdventure)) return false;

        QuickReservationAdventure successfullQuickReservation=new QuickReservationAdventure(quickReservationAdventure.getId(),quickReservationAdventure.getStartDate(),
                quickReservationAdventure.getEndDate(),null,quickReservationAdventure.getPaymentInformation(),
                quickReservationAdventure.getOwnersReport(),quickReservationAdventure.getAdventure(),quickReservationAdventure.getFishingInstructor(),quickReservationAdventure.getDiscount(),null);

        quickReservationAdventureRepository.save(successfullQuickReservation);
        if(quickReservationAdventure.getAddedAdditionalServices()!=null){
            successfullQuickReservation.setAddedAdditionalServices(quickReservationAdventure.getAddedAdditionalServices());
            quickReservationAdventureRepository.save(successfullQuickReservation);
        }
        //TO DO: poslati mejl onima koji su pretplaceni na akcije od tog cabina

        return true;
    }

    @Override
    public Set<QuickReservationAdventure> getByInstructorId(Long id) {
        return  quickReservationAdventureRepository.getByInstructorId(id,LocalDateTime.now());
    }

    @Override
    public boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationAdventureRepository.quickReservationExists(id,startDate,endDate);
    }

    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate,Long id) {
        return quickReservationAdventureRepository.futureQuickReservationsExist(currentDate,id);
    }
    @Override
    public Set<QuickReservationAdventure> getAllReports(){
        return  quickReservationAdventureRepository.getAllReports();
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, Long ownerId) {
        return quickReservationAdventureRepository.countReservationsInPeriod(start,end,ownerId);
    }

    @Override
    public Double sumProfit(Long ownerId, LocalDateTime start, LocalDateTime end) {
        return quickReservationAdventureRepository.sumProfit(ownerId,start,end);
    }


    @Override
    public Set<QuickReservationAdventure> getPastReservations(Long instructorId) {

        return quickReservationAdventureRepository.getPastReservations(instructorId,LocalDateTime.now());
    }

    @Override
    public void ownerCreatesReview(QuickReservationAdventure reservation, boolean successfull) {
        QuickReservationAdventure adventureReservation = quickReservationAdventureRepository.getById(reservation.getId());
        adventureReservation.setSuccessfull(successfull);
        adventureReservation.getOwnersReport().setComment(reservation.getOwnersReport().getComment());
        adventureReservation.getOwnersReport().setBadComment(reservation.getOwnersReport().isBadComment());
        quickReservationAdventureRepository.save(adventureReservation);
    }

    private boolean validateForReservation(QuickReservationAdventure quickReservationAdventure){
        if(!availableInstructorPeriodService.instructorIsAvailable(quickReservationAdventure.getFishingInstructor()
                .getId(),quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        if(adventureReservationService.reservationExists(quickReservationAdventure.getFishingInstructor()
                .getId(),quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        if(quickReservationAdventureRepository.quickReservationExists(quickReservationAdventure.getFishingInstructor().getId()
                ,quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        return true;
    }
}
