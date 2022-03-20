package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.*;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AdditionalServicesService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatOwnerService;
import rs.ac.uns.ftn.isa.fisherman.service.BoatService;
import rs.ac.uns.ftn.isa.fisherman.service.ImageService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BoatServiceImpl implements BoatService {
    @Autowired
    private BoatRepository boatRepository;
    @Autowired
    private ImageService imageService;
    @Autowired
    private AdditionalServicesService additionalServicesService;
    @Autowired
    private BoatOwnerService boatOwnerService;

    @Override
    public void save(Boat boat) {
        boatRepository.save(boat);
    }

    @Override
    public Boat findById(Long id) {
       return boatRepository.findById(id);
    }

    @Override
    public void addNewImage(String boatName, Image image) {
        Boat boat= boatRepository.findByName(boatName);
        Set<Image> currentImages=boat.getImages();
        currentImages.add(image);
        boatRepository.save(boat);
    }

    @Override
    public Set<Boat> findByOwnersId(Long id) {
        return boatRepository.findByOwnersId(id);
    }

    @Override
    public Boat findByName(String name) {
        return boatRepository.findByName(name);
    }



    @Override
    public Boat findByNameAndOwner(String boatName, Long boatOwner) {
        return boatRepository.findByNameAndOwner(boatName, boatOwner);
    }

    @Override
    public void edit(Boat newBoat, Boolean deleteOldImages) {
        Boat oldBoat=this.boatRepository.findByNameAndOwner(newBoat.getName(),newBoat.getBoatOwner().getId());
        oldBoat.setType(newBoat.getType());
        oldBoat.setLength(newBoat.getLength());
        oldBoat.setEngineCode(newBoat.getEngineCode());
        oldBoat.setEnginePower(newBoat.getEnginePower());
        oldBoat.setMaxSpeed(newBoat.getMaxSpeed());
        oldBoat.setNavigationEquipment(newBoat.getNavigationEquipment());
        oldBoat.setAddress(newBoat.getAddress());
        oldBoat.setDescription(newBoat.getDescription());
        oldBoat.setMaxPeople(newBoat.getMaxPeople());
        oldBoat.setRules(newBoat.getRules());
        oldBoat.setFishingEquipment(newBoat.getFishingEquipment());
        oldBoat.setPrice(newBoat.getPrice());
        oldBoat.setCancelingCondition(newBoat.getCancelingCondition());
        Set<AdditionalServices> oldAdditionalServices=oldBoat.getAdditionalServices();
        Set<Image> oldImages= oldBoat.getImages();
        oldBoat.setAdditionalServices(newBoat.getAdditionalServices());
        if(Boolean.TRUE.equals(deleteOldImages))  oldBoat.setImages(new HashSet<>());
        boatRepository.save(oldBoat);
        Set<AdditionalServices> savedServices= boatRepository.findByName(oldBoat.getName()).getAdditionalServices();
        if(Boolean.TRUE.equals(deleteOldImages))   imageService.delete(oldImages);
        additionalServicesService.delete(additionalServicesService.findDeletedAdditionalServices(oldAdditionalServices,savedServices));
    }

    @Override
    public void delete(Long id) {
        Boat boat=boatRepository.findById(id);
        Set<AdditionalServices> additionalServices=boat.getAdditionalServices();
        Set<Image> images=boat.getImages();
        boatRepository.delete(boat);
        imageService.delete(images);
        additionalServicesService.delete(additionalServices);
    }

    public List<Boat> findAll(){
        return boatRepository.findAll();
    }
}
