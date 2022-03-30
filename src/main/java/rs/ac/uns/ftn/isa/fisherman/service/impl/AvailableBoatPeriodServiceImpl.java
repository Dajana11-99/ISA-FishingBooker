package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.AvailableBoatPeriodRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableBoatPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatReservationService;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class AvailableBoatPeriodServiceImpl implements AvailableBoatPeriodService {
    @Autowired
    private AvailableBoatPeriodRepository availableBoatPeriodRepository;
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private BoatOwnerService boatOwnerService;

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
    public boolean editAvailableBoatsPeriod(AvailableBoatPeriod oldAvailablePeriod, AvailableBoatPeriod newAvailablePeriod) {
        return true;
    }
    public boolean boatIsAvailable(Long boatId, LocalDateTime start,  LocalDateTime end){
        return availableBoatPeriodRepository.boatIsAvailable(boatId,start,end);
    }
    private boolean reservationsDontExistInPeriod(AvailableCabinPeriod availablePeriod){
        if(boatReservationService.reservationExists(availablePeriod.getCabin().getId()
                ,availablePeriod.getStartDate(),availablePeriod.getEndDate())) return false;

/*
        if(quickReservationCabinService.quickReservationExists(availablePeriod.getCabin().getId(),
                availablePeriod.getStartDate(),availablePeriod.getEndDate())) return false;
*/
        return true;
    }


}
