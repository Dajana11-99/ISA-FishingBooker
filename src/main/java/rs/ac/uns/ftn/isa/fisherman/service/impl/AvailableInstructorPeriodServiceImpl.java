package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;
import rs.ac.uns.ftn.isa.fisherman.repository.AvailableInstructorPeriodRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service
public class AvailableInstructorPeriodServiceImpl implements AvailableInstructorPeriodService {
    @Autowired
    AvailableInstructorPeriodRepository availableInstructorPeriodRepository;
    @Autowired
    AdventureReservationService adventureReservationService;
    @Autowired
    QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private AvailablePeriodService availablePeriodService;
    @Autowired
    FishingInstructorService fishingInstructorService;


    @Override
    public Set<AvailableInstructorPeriod> getAvailablePeriod(String username) {
        Long instructorId= fishingInstructorService.findByUsername(username).getId();
        return  availableInstructorPeriodRepository.findByInstructorId(instructorId);
    }

    @Override
    public boolean setAvailableInstructorPeriod(AvailableInstructorPeriod availableInstructorPeriod) {

        if(availableInstructorPeriodRepository.availablePeriodAlreadyExists(availableInstructorPeriod.getFishingInstructor().getId(),
                availableInstructorPeriod.getStartDate(),availableInstructorPeriod.getEndDate())) return false;
        availableInstructorPeriodRepository.save(availableInstructorPeriod);
        return true;
    }

    @Override
    public boolean instructorIsAvailable(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        if(!availableInstructorPeriodRepository.instructorIsAvailable(id,startDate,endDate).isEmpty())
            return true;
        return  false;
    }

    @Override
    public boolean deleteAvailableBoatsPeriod(AvailableInstructorPeriod availableInstructorPeriod) {
        AvailableInstructorPeriod availableInstructorPeriodToDelete= availableInstructorPeriodRepository
                .findId(availableInstructorPeriod.getFishingInstructor().getId(),
                availableInstructorPeriod.getStartDate(),availableInstructorPeriod.getEndDate());
        if(availableInstructorPeriodToDelete == null)return false;

        if(!reservationsDontExistInPeriod(availableInstructorPeriodToDelete)) return false;

        availablePeriodService.deleteAvailablePeriod(availableInstructorPeriodToDelete.getId());
        return true;
    }

    private boolean reservationsDontExistInPeriod(AvailableInstructorPeriod availableInstructorPeriod){
        if(adventureReservationService.reservationExists(availableInstructorPeriod.getFishingInstructor().getUsername()
                ,availableInstructorPeriod.getStartDate(),availableInstructorPeriod.getEndDate())) {

            return false;
        }


        if(quickReservationAdventureService.quickReservationExists(availableInstructorPeriod.getFishingInstructor().getUsername(),
                availableInstructorPeriod.getStartDate(),availableInstructorPeriod.getEndDate())) return false;

        return true;
    }

    @Override
    public boolean editAvailableInstructorsPeriod(AvailableInstructorPeriod oldPeriod, AvailableInstructorPeriod newPeriod) {
        AvailableInstructorPeriod availableInstructorPeriodToEdit= availableInstructorPeriodRepository.findId(oldPeriod.getFishingInstructor().getId(),
                oldPeriod.getStartDate(),oldPeriod.getEndDate());
        if(availableInstructorPeriodToEdit == null) return false;
        if(!reservationsDontExistInPeriod(newPeriod)) return false;
        if (!availableInstructorPeriodRepository.availablePeriodIncludesUnavailable(availableInstructorPeriodToEdit.getId(),
                newPeriod.getStartDate(),newPeriod.getEndDate())){
            return false;
        }
        LocalDateTime startOld=oldPeriod.getStartDate();
        LocalDateTime endOld=oldPeriod.getEndDate();

        LocalDateTime startNew=newPeriod.getStartDate();
        LocalDateTime endNew=newPeriod.getEndDate();

        if(startOld.equals(startNew)){
            availableInstructorPeriodToEdit.setStartDate(endNew.plusMinutes(1));
        }else if(endOld.equals(endNew)){
            availableInstructorPeriodToEdit.setEndDate(startNew.minusMinutes(1));
        }else {
            availableInstructorPeriodToEdit.setEndDate(startNew.minusMinutes(1));
            newPeriod.setEndDate(endOld);
            newPeriod.setStartDate(endNew.plusMinutes(1));
            availableInstructorPeriodRepository.save(newPeriod);
        }
        availableInstructorPeriodRepository.save(availableInstructorPeriodToEdit);
        return true;
    }

    @Override
    public List<Long> getAvailableFishingInstructorsIdsForPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return availableInstructorPeriodRepository.getAvailableFishingInstructorsForPeriod( startDate, endDate);
    }

}
