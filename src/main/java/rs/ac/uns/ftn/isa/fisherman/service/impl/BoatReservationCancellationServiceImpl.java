package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.BoatReservationDto;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationBoatDto;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatReservationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationBoatRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class BoatReservationCancellationServiceImpl implements BoatReservationCancellationService {

    @Autowired
    private BoatReservationCancellationRepository boatReservationCancellationRepository;
    @Autowired
    private ClientService clientService;
    @Autowired
    private BoatReservationRepository boatReservationRepository;
    @Autowired
    private QuickReservationBoatRepository quickReservationBoatRepository;
    @Autowired
    private ReservationPaymentService reservationPaymentService;
    @Autowired
    private PenaltyService penaltyService;

    @Override
    public boolean addCancellation(BoatReservationDto boatReservationDto) {
        if(cancellationPossible(boatReservationDto.getStartDate()))
            return false;
        BoatReservation boatReservation = boatReservationRepository.getById(boatReservationDto.getId());
        BoatReservationCancellation boatReservationCancellation = new BoatReservationCancellation(null, boatReservation.getClient(), boatReservationDto.getStartDate(), boatReservationDto.getEndDate(), boatReservation.getBoat());
        boatReservation.setAddedAdditionalServices(new HashSet<>());
        reservationPaymentService.resetLoyaltyStatusAfterCancellation(boatReservation.getClient(), boatReservation.getBoat().getBoatOwner());
        boatReservationRepository.save(boatReservation);
        boatReservationRepository.deleteByReservationId(boatReservation.getId());
        boatReservationCancellationRepository.save(boatReservationCancellation);
        penaltyService.addPenalty(boatReservation.getClient().getUsername());
        return true;
    }
    private boolean cancellationPossible(LocalDateTime startDate){
        return startDate.minusDays(3).isBefore(LocalDateTime.now());
    }
    @Override
    public boolean addCancellationQuickReservation(QuickReservationBoatDto boatReservationDto) {
        if(cancellationPossible(boatReservationDto.getStartDate()))
            return false;
        QuickReservationBoat quickReservationBoat = quickReservationBoatRepository.getById(boatReservationDto.getId());
        BoatReservationCancellation boatReservationCancellation = new BoatReservationCancellation(null, quickReservationBoat.getClient(), quickReservationBoat.getStartDate(), quickReservationBoat.getEndDate(), quickReservationBoat.getBoat());
        reservationPaymentService.resetLoyaltyStatusAfterCancellation(quickReservationBoat.getClient(), quickReservationBoat.getBoat().getBoatOwner());
        penaltyService.addPenalty(quickReservationBoat.getClient().getUsername());
        quickReservationBoat.setClient(null);
        quickReservationBoatRepository.save(quickReservationBoat);
        boatReservationCancellationRepository.save(boatReservationCancellation);
        return true;
    }
    
    @Override
    public boolean clientHasCancellationForBoatInPeriod(Long boatId, String clientUsername, LocalDateTime startDate, LocalDateTime endDate) {
        return boatReservationCancellationRepository.clientHasCancellationForBoatInPeriod(boatId, clientService.findByUsername(clientUsername).getId(), startDate, endDate);
    }
}
