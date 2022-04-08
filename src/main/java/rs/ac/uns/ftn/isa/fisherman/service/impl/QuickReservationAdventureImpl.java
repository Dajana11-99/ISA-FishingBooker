package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
                quickReservationAdventure.getEndDate(),null,quickReservationAdventure.getPaymentInformation(),quickReservationAdventure.isOwnerWroteAReport(),
                quickReservationAdventure.getOwnersUsername(),
                quickReservationAdventure.getAdventure(),quickReservationAdventure.getFishingInstructor(),quickReservationAdventure.getDiscount(),null);
        successfullQuickReservation.setEvaluated(false);
        quickReservationAdventureRepository.save(successfullQuickReservation);
        if(quickReservationAdventure.getAddedAdditionalServices()!=null){
            successfullQuickReservation.setAddedAdditionalServices(quickReservationAdventure.getAddedAdditionalServices());
            quickReservationAdventureRepository.save(successfullQuickReservation);
        }
        //TO DO: poslati mejl onima koji su pretplaceni na akcije od tog cabina

        return true;
    }

    @Override
    public Set<QuickReservationAdventure> getByInstructorUsername(String username) {
        return  quickReservationAdventureRepository.getByInstructorUsername(username,LocalDateTime.now());
    }

    @Override
    public boolean quickReservationExists(String username, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationAdventureRepository.quickReservationExists(username,startDate,endDate);
    }

    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate,Long id) {
        return quickReservationAdventureRepository.futureQuickReservationsExist(currentDate,id);
    }

    @Override
    public Integer countReservationsInPeriod(LocalDateTime start, LocalDateTime end, String username) {
        return quickReservationAdventureRepository.countReservationsInPeriod(start,end,username);
    }

    @Override
    public Double sumProfit(String username, LocalDateTime start, LocalDateTime end) {
        return quickReservationAdventureRepository.sumProfit(username,start,end);
    }

    @Override
    public void save(QuickReservationAdventure quickReservationAdventure) {
        quickReservationAdventureRepository.save(quickReservationAdventure);
    }

    @Override
    public QuickReservationAdventure findById(Long id) {
        return quickReservationAdventureRepository.getById(id);
    }


    @Override
    public Set<QuickReservationAdventure> getPastReservations(String username) {
        return quickReservationAdventureRepository.getPastReservations(username,LocalDateTime.now());
    }


    private boolean validateForReservation(QuickReservationAdventure quickReservationAdventure){
        if(!availableInstructorPeriodService.instructorIsAvailable(quickReservationAdventure.getFishingInstructor()
                .getId(),quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        if(adventureReservationService.reservationExists(quickReservationAdventure.getOwnersUsername(),quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        if(quickReservationAdventureRepository.quickReservationExists(quickReservationAdventure.getOwnersUsername()
                ,quickReservationAdventure.getStartDate(),quickReservationAdventure.getEndDate())) return false;

        return true;
    }
}
