package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.AdventureRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdventureServiceImpl implements AdventureService {

    @Autowired
    private AdventureRepository adventureRepository;

    @Autowired
    private FishingInstructorService fishingInstructorService;
    @Autowired
    private AdventureReservationService adventureReservationService;
    @Autowired
    private QuickReservationAdventureService quickReservationAdventureService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AdditionalServicesService additionalServicesService;

    public void save(Adventure adventure){
       adventureRepository.save(adventure);
    }

    public List<Adventure> findAll(){
        return adventureRepository.findAll();
    }

    @Override
    public boolean addNewAdventure(Adventure adventure,Set<AdditionalServices>additionalServices) {
      adventureRepository.save(adventure);
        if(additionalServices!=null) {
            adventure.setAdditionalServices(additionalServices);
            adventureRepository.save(adventure);
        }
      return true;
    }

    @Override
    public Adventure findById(Long id) {
        return adventureRepository.findByID(id);
    }

    @Override
    public List<Adventure> findAdventuresByInstructorIds(List<Long> instructorIds) {
        return adventureRepository.findAdventuresByInstructorIds(instructorIds);
    }

    @Override
    public Adventure findByName(String adventureName) {
        return adventureRepository.findByName(adventureName);
    }

    @Override
    public void addNewImage(String adventureName, Image image) {
        Adventure adventure= adventureRepository.findByName(adventureName);
        Set<Image> currentImages=adventure.getImages();
        currentImages.add(image);
        adventureRepository.save(adventure);
    }
    public Set<Adventure> findAdventuresByInstructorId(Long id){
        return adventureRepository.findAdventuresByInstructorId(id);
    }

    @Override
    public Adventure findAdventureByName(String name, Long fishingInstructorId) {
        return adventureRepository.findAdventureByName(name,fishingInstructorId);
    }

    @Override
    public void delete(Long id) {
        Adventure adventure=adventureRepository.findByID(id);
        Set<AdditionalServices> additionalServices=adventure.getAdditionalServices();
        Set<Image> images=adventure.getImages();
        adventureRepository.delete(adventure);
        additionalServicesService.delete(additionalServices);
        imageService.delete(images);
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public boolean edit(Adventure adventure, Long instructorId) throws Exception{
        Adventure oldAdventure= adventureRepository.findAdventureByName(adventure.getName(),instructorId);
        oldAdventure.setDescription(adventure.getDescription());
        oldAdventure.setAddress(adventure.getAddress());
        oldAdventure.setEquipment(adventure.getEquipment());
        oldAdventure.setCancelingCondition(adventure.getCancelingCondition());
        oldAdventure.setInstructorsBiography(adventure.getInstructorsBiography());
        oldAdventure.setRules(adventure.getRules());
        oldAdventure.setMaxPeople(adventure.getMaxPeople());
        oldAdventure.setPrice(adventure.getPrice());
        Set<AdditionalServices> oldAdditionalServices=oldAdventure.getAdditionalServices();
        Set<Image> oldImages= oldAdventure.getImages();
        oldAdventure.setAdditionalServices(adventure.getAdditionalServices());
        if(adventure.getImages()==null)  oldAdventure.setImages(new HashSet<>());
        adventureRepository.save(oldAdventure);

        Set<AdditionalServices> savedServices=adventureRepository.findAdventureByName(adventure.getName(),instructorId).getAdditionalServices();
        if(adventure.getImages()==null)   imageService.delete(oldImages);
        additionalServicesService.delete(additionalServicesService.findDeletedAdditionalServices(oldAdditionalServices,savedServices));
        return  true;
    }
    @Override
    public boolean canBeEditedOrDeleted(Long id) {
        LocalDateTime currentDate=LocalDateTime.now();
        if(adventureReservationService.futureReservationsExist(currentDate,id)) return false;
        if(quickReservationAdventureService.futureQuickReservationsExist(currentDate,id)) return false;
        return true;
    }

}
