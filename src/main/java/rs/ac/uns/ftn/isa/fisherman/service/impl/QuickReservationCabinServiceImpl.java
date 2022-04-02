package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationCabinRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AvailableCabinPeriodService;
import rs.ac.uns.ftn.isa.fisherman.service.QuickReservationCabinService;
import rs.ac.uns.ftn.isa.fisherman.service.ReservationCabinService;

import java.time.LocalDateTime;
import java.util.Set;

@Service
public class QuickReservationCabinServiceImpl implements QuickReservationCabinService {

    @Autowired
    private AvailableCabinPeriodService availableCabinPeriodService;
    @Autowired
    private ReservationCabinService reservationCabinService;
    @Autowired
    private QuickReservationCabinRepository quickReservationCabinRepository;
    @Override
    public boolean ownerCreates(QuickReservationCabin quickReservationCabin) {
        if(!validateForReservation(quickReservationCabin)) return false;

        QuickReservationCabin successfullQuickReservation=new QuickReservationCabin(quickReservationCabin.getId(),quickReservationCabin.getStartDate(),
                quickReservationCabin.getEndDate(),quickReservationCabin.getPrice(),quickReservationCabin.getCabin(),null,quickReservationCabin.getDiscount());
        successfullQuickReservation.setClient(null);
        quickReservationCabinRepository.save(successfullQuickReservation);
        if(quickReservationCabin.getAddedAdditionalServices()!=null){
            successfullQuickReservation.setAddedAdditionalServices(quickReservationCabin.getAddedAdditionalServices());
            quickReservationCabinRepository.save(successfullQuickReservation);
        }
        //TO DO: poslati mejl onima koji su pretplaceni na akcije od tog cabina

        return true;
    }

    @Override
    public Set<QuickReservationCabin> getByCabinId(Long cabinId) {
        return quickReservationCabinRepository.getByCabinId(cabinId);
    }

    @Override
    public boolean quickReservationExists(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        return quickReservationCabinRepository.quickReservationExists(id,startDate,endDate);
    }

    @Override
    public boolean futureQuickReservationsExist(LocalDateTime currentDate, Long id) {
        return quickReservationCabinRepository.futureQuickReservationsExist(currentDate,id);
    }

    @Override
    public Set<QuickReservationCabin> findReservationsByOwnerId(Long id) {
        return quickReservationCabinRepository.findReservationsByOwnerId(id,LocalDateTime.now());
    }

    @Override
    public Set<QuickReservationCabin> getPastReservations(Long id) {
        return quickReservationCabinRepository.getPastReservations(id,LocalDateTime.now());
    }

    private boolean validateForReservation(QuickReservationCabin cabinQuickReservation){
        if(!availableCabinPeriodService.cabinIsAvailable(cabinQuickReservation.getCabin()
                .getId(),cabinQuickReservation.getStartDate(),cabinQuickReservation.getEndDate())) return false;

        if(reservationCabinService.reservationExists(cabinQuickReservation.getCabin()
                .getId(),cabinQuickReservation.getStartDate(),cabinQuickReservation.getEndDate())) return false;

        if(quickReservationCabinRepository.quickReservationExists(cabinQuickReservation.getCabin()
                .getId(),cabinQuickReservation.getStartDate(),cabinQuickReservation.getEndDate())) return false;

        return true;
    }
}
