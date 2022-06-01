package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.BoatOwnersReservationReport;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatOwnersReservationReportRepository;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnersReservationReportService;
@Service
public class BoatOwnersReservationReportServiceImpl implements BoatOwnersReservationReportService {
    @Autowired
    private BoatOwnersReservationReportRepository boatOwnersReservationReportRepository;
    @Override
    public void save(BoatOwnersReservationReport reservationReport) {
        boatOwnersReservationReportRepository.save(reservationReport);
    }
}
