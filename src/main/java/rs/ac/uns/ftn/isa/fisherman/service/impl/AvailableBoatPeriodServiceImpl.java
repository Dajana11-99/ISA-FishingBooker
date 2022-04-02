package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.AvailableBoatPeriodRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AvailableBoatPeriodServiceImpl implements AvailableBoatPeriodService {
    @Autowired
    private AvailableBoatPeriodRepository availableBoatPeriodRepository;
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private QuickReservationBoatService quickReservationBoatService;
    @Autowired
    private AvailablePeriodService availablePeriodService;

    @Override
    public Set<AvailableBoatPeriod> getAvailablePeriod(Long id) {
        return  availableBoatPeriodRepository.findByBoatId(id);
    }

    @Override
    public boolean setAvailableBoatPeriod(AvailableBoatPeriod availablePeriod) {
        if(availableBoatPeriodRepository.availablePeriodAlreadyExists(availablePeriod.getBoat().getId(),
                availablePeriod.getStartDate(),availablePeriod.getEndDate())) return false;
        availableBoatPeriodRepository.save(availablePeriod);
        return true;
    }

    @Override
    public boolean editAvailableBoatsPeriod(AvailableBoatPeriod oldPeriod, AvailableBoatPeriod newPeriod) {
        AvailableBoatPeriod availableBoatPeriodToEdit= availableBoatPeriodRepository.findId(oldPeriod.getBoat().getId(),
                oldPeriod.getStartDate(),oldPeriod.getEndDate());
        if(availableBoatPeriodToEdit == null) return false;
        if(!reservationsDontExistInPeriod(newPeriod)) return false;
        if (!availableBoatPeriodRepository.availablePeriodIncludesUnavailable(availableBoatPeriodToEdit.getId(),
                newPeriod.getStartDate(),newPeriod.getEndDate())){
            return false;
        }
        LocalDateTime startOld=oldPeriod.getStartDate();
        LocalDateTime endOld=oldPeriod.getEndDate();

        LocalDateTime startNew=newPeriod.getStartDate();
        LocalDateTime endNew=newPeriod.getEndDate();

        if(startOld.equals(startNew)){
            availableBoatPeriodToEdit.setStartDate(endNew.plusMinutes(1));
        }else if(endOld.equals(endNew)){
            availableBoatPeriodToEdit.setEndDate(startNew.minusMinutes(1));
        }else {
            availableBoatPeriodToEdit.setEndDate(startNew.minusMinutes(1));
            newPeriod.setEndDate(endOld);
            newPeriod.setStartDate(endNew.plusMinutes(1));
            availableBoatPeriodRepository.save(newPeriod);
        }
        availableBoatPeriodRepository.save(availableBoatPeriodToEdit);
        return true;
    }
    @Override
    public boolean deleteAvailableBoatsPeriod(AvailableBoatPeriod availablePeriod) {
        AvailableBoatPeriod availableBoatPeriodToDelete= availableBoatPeriodRepository.findId(availablePeriod.getBoat().getId(),
                availablePeriod.getStartDate(),availablePeriod.getEndDate());
        if(availableBoatPeriodToDelete == null) return false;

        if(!reservationsDontExistInPeriod(availableBoatPeriodToDelete)) return false;

        availablePeriodService.deleteAvailablePeriod(availableBoatPeriodToDelete.getId());
        return true;
    }
    public boolean boatIsAvailable(Long boatId, LocalDateTime start,  LocalDateTime end){
        return availableBoatPeriodRepository.boatIsAvailable(boatId,start,end);
    }
    private boolean reservationsDontExistInPeriod(AvailableBoatPeriod availablePeriod){
        if(boatReservationService.reservationExists(availablePeriod.getBoat().getId()
                ,availablePeriod.getStartDate(),availablePeriod.getEndDate())) return false;


        if(quickReservationBoatService.quickReservationExists(availablePeriod.getBoat().getId(),
                availablePeriod.getStartDate(),availablePeriod.getEndDate())) return false;

        return true;
    }


}
