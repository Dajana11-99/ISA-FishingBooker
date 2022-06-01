package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.InstructorReservationReport;
import rs.ac.uns.ftn.isa.fisherman.repository.InstructorReservationReportRepository;
import rs.ac.uns.ftn.isa.fisherman.service.InstructorReservationReportService;

@Service
public class InstructorReservationReportImpl implements InstructorReservationReportService {
    @Autowired
    private InstructorReservationReportRepository instructorReservationReportRepository;

    @Override
   public void save(InstructorReservationReport reservationReport){
        instructorReservationReportRepository.save(reservationReport);

    }
}
