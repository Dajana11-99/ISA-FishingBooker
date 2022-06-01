package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinReservationDto;
import rs.ac.uns.ftn.isa.fisherman.dto.QuickReservationCabinDto;
import rs.ac.uns.ftn.isa.fisherman.model.QuickReservationCabin;
import rs.ac.uns.ftn.isa.fisherman.repository.QuickReservationCabinRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservationCancellation;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationCancellationRepository;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinReservationRepository;

import java.time.LocalDateTime;
import java.util.HashSet;

@Service
public class CabinReservationCancellationServiceImpl implements CabinReservationCancellationService {

    @Autowired
    private CabinReservationCancellationRepository cabinReservationCancellationRepository;
    @Autowired
    private CabinReservationRepository cabinReservationRepository;
    @Autowired
    private CabinOwnerService cabinOwnerService;
    @Autowired
    private ReservationPaymentService reservationPaymentService;
    @Autowired
    private PenaltyService penaltyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private QuickReservationCabinRepository quickReservationCabinRepository;
    @Override
    public boolean addCancellation(CabinReservationDto cabinReservationDto) {
        CabinReservation cabinReservation = cabinReservationRepository.getById(cabinReservationDto.getId());
        CabinReservationCancellation cabinReservationCancellation = new CabinReservationCancellation(null, cabinReservation.getClient(), cabinReservation.getStartDate(), cabinReservation.getEndDate(), cabinReservation.getCabin());
        cabinReservation.setAddedAdditionalServices(new HashSet<>());
        reservationPaymentService.resetLoyaltyStatusAfterCancellation(cabinReservation.getClient(), cabinOwnerService.findByUsername(cabinReservationDto.getCabinDto().getOwnerUsername()));
        cabinReservationRepository.save(cabinReservation);
        cabinReservationRepository.deleteByReservationId(cabinReservation.getId());
        cabinReservationCancellationRepository.save(cabinReservationCancellation);
        penaltyService.addPenalty(cabinReservation.getClient().getUsername());
        return true;
    }

    @Override
    public boolean clientHasCancellationForCabinInPeriod(Long cabinId, String clientUsername, LocalDateTime startDate, LocalDateTime endDate) {
        return cabinReservationCancellationRepository.clientHasCancellationForCabinInPeriod(cabinId, clientService.findByUsername(clientUsername).getId(), startDate, endDate);
    }

    @Override
    public boolean addCancellationQuickReservation(QuickReservationCabinDto cabinReservationDto) {
        QuickReservationCabin quickReservationCabin = quickReservationCabinRepository.getById(cabinReservationDto.getId());
        CabinReservationCancellation cabinReservationCancellation = new CabinReservationCancellation(null, quickReservationCabin.getClient(), quickReservationCabin.getStartDate(), quickReservationCabin.getEndDate(), quickReservationCabin.getCabin());
        reservationPaymentService.resetLoyaltyStatusAfterCancellation(quickReservationCabin.getClient(), cabinOwnerService.findByUsername(cabinReservationDto.getCabinDto().getOwnerUsername()));
        penaltyService.addPenalty(quickReservationCabin.getClient().getUsername());
        quickReservationCabin.setClient(null);
        quickReservationCabinRepository.save(quickReservationCabin);
        cabinReservationCancellationRepository.save(cabinReservationCancellation);
        return true;
    }
}
