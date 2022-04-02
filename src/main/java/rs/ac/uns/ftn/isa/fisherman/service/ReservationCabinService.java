package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.dto.SearchAvailablePeriodsCabinDto;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.dto.CabinReservationDto;
import rs.ac.uns.ftn.isa.fisherman.model.CabinReservation;

import java.time.LocalDateTime;
import java.util.Set;

public interface ReservationCabinService {

    Set<Cabin> getAvailableCabins(SearchAvailablePeriodsCabinDto searchAvailablePeriodsCabinDto);
    boolean makeReservation(CabinReservationDto cabinReservationDto);

    boolean ownerCreates(CabinReservation cabinReservation, String clientUsername);

    Set<CabinReservation> getPresentByCabinId(Long cabinId);
    boolean reservationExists(Long cabinId, LocalDateTime startDate, LocalDateTime endDate);

    Set<CabinReservation> getUpcomingClientReservationsByUsername(String clientUsername);
    Set<CabinReservation> getClientReservationHistoryByUsername(String clientUsername);

    boolean futureReservationsExist(LocalDateTime currentDate, Long id);

    Set< CabinReservation> findReservationsByOwnerId(Long id);
    Set<CabinReservation> getPastReservations(Long id);
}
