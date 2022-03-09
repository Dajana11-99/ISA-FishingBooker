package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AvailableInstructorPeriod;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;
import rs.ac.uns.ftn.isa.fisherman.repository.FishingInstructorRepository;
import rs.ac.uns.ftn.isa.fisherman.service.FishingInstructorService;

import java.util.List;
import java.util.Set;

@Service
public class FishingInstructorServiceImpl implements FishingInstructorService {
    private FishingInstructorRepository fishingInstructorRepository;
    @Autowired
    public FishingInstructorServiceImpl(FishingInstructorRepository fishingInstructorRepository) {
        this.fishingInstructorRepository = fishingInstructorRepository;
    }

    @Override
    public List<FishingInstructor> getNewFishingInstructors() {return fishingInstructorRepository.getNewFishingInstructor();}

    @Override
    public List<FishingInstructor> getNewActiveInstructors() {return  fishingInstructorRepository.getActiveFishingInstructor();}

    @Override
    public FishingInstructor findByUsername(String fishingInstructorUsername) {
       return  fishingInstructorRepository.findByUsername(fishingInstructorUsername);
    }

    @Override
    public FishingInstructor findByID(Long id) {
        return  fishingInstructorRepository.findByID(id);
    }

    @Override
    public void save(FishingInstructor fishingInstructor) {
        fishingInstructorRepository.save(fishingInstructor);
    }


}
