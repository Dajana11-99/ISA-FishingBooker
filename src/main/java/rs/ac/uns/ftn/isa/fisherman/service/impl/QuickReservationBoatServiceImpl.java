package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.BoatReservation;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationBoat;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationBoatRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableBoatPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatReservationService;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationBoatService;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class QuickReservationBoatServiceImpl implements QuickReservationBoatService {
    @Autowired
    private AvailableBoatPeriodService availableBoatPeriodService;
    @Autowired
    private BoatReservationService boatReservationService;
    @Autowired
    private QuickReservationBoatRepository quickReservationBoatRepository;

    @Override
    public boolean ownerCreates(QuickReservationBoat quickReservationBoat) {
        if(!validateForReservation(quickReservationBoat)) return false;

        QuickReservationBoat successfullQuickReservation=new QuickReservationBoat(quickReservationBoat.getId(), quickReservationBoat.getStartDate(),
                quickReservationBoat.getEndDate(), quickReservationBoat.getPrice(), quickReservationBoat.getBoat(),null, quickReservationBoat.getNeedsCaptainServices(),quickReservationBoat.getDiscount());
        successfullQuickReservation.setClient(null);
        if(quickReservationBoat.getAddedAdditionalServices()!=null){
            if(quickReservationBoat.getNeedsCaptainServices()) {
                if (ownerIsNotAvailable(successfullQuickReservation.getBoat().getBoatOwner().getId(),
                        successfullQuickReservation.getStartDate(), successfullQuickReservation.getEndDate())) return false;
            }
            quickReservationBoatRepository.save(successfullQuickReservation);
            successfullQuickReservation.setAddedAdditionalServices(quickReservationBoat.getAddedAdditionalServices());
            quickReservationBoatRepository.save(successfullQuickReservation);
        }else{
            quickReservationBoatRepository.save(successfullQuickReservation);
        }
        //TO DO: poslati mejl onima koji su pretplaceni na akcije

        return true;
    }
    private boolean ownerIsNotAvailable(Long ownerId, LocalDateTime start, LocalDateTime end){
        if (quickReservationBoatRepository.ownerIsNotAvailable(ownerId, start, end)) return true;

        if(boatReservationService.ownerIsNotAvailableReservation(ownerId, start, end)) return true;
        return  false;
    }
    @Override
    public boolean ownerIsNotAvailableQuickResrvation(Long ownerId, LocalDateTime start, LocalDateTime end){
        return quickReservationBoatRepository.ownerIsNotAvailable(ownerId, start, end);
    }

    @Override
    public Set<QuickReservationBoat> findReservationsByOwnerId(Long id) {
        LocalDateTime currentDate=LocalDateTime.now();
        return quickReservationBoatRepository.findReservationsByOwnerId(id,currentDate);
    }

    @Override
    public Set<QuickReservationBoat> getByBoatId(Long cabinId) {
        return quickReservationBoatRepository.getByBoatId(cabinId);
    }

    @Override
    public boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationBoatRepository.quickReservationExists(id,startDate,endDate);
    }

    private boolean validateForReservation(QuickReservationBoat quickReservationBoat){
        if(!availableBoatPeriodService.boatIsAvailable(quickReservationBoat.getBoat()
                .getId(),quickReservationBoat.getStartDate(),quickReservationBoat.getEndDate())) return false;

        if(boatReservationService.reservationExists(quickReservationBoat.getBoat()
                .getId(),quickReservationBoat.getStartDate(),quickReservationBoat.getEndDate())) return false;

        if(quickReservationBoatRepository.quickReservationExists(quickReservationBoat.getBoat()
                .getId(),quickReservationBoat.getStartDate(),quickReservationBoat.getEndDate())) return false;

        return true;
    }
    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate,Long boatId){
        return quickReservationBoatRepository.futureQuickReservationsExist(currentDate,boatId);
    }

    @Override
    public Set<QuickReservationBoat> getPastReservations(Long id) {
        LocalDateTime currentDate=LocalDateTime.now();
        return quickReservationBoatRepository.getPastReservations(id, currentDate);
    }
}
