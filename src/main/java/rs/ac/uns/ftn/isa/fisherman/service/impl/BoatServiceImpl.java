package rs.ac.uns.ftn.isa.fisherman.service.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;
import rs.ac.uns.ftn.isa.fisherman.model.Image;
import rs.ac.uns.ftn.isa.fisherman.repository.BoatRepository;
import rs.ac.uns.ftn.isa.fisherman.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private QuickReservationBoatService quickReservationBoatService;
    @Autowired
    private BoatReservationService boatReservationService;

    @Override
    public void save(Boat boat) {
        boatRepository.save(boat);
    }

    @Override
    public boolean addNewBoat(Boat boat, Set<AdditionalServices> additionalServices) {
        boolean services=false;
        if(boatRepository.findByNameAndOwner(boat.getName(),boat.getBoatOwner().getId())!=null)  return false;

        boatRepository.save(boat);
            if (additionalServices != null) {
                boat.setAdditionalServices(additionalServices);
                services = true;
            }
            if (services) boatRepository.save(boat);
       return true;
    }

    @Override
    public Boat findById(Long id) {
       return boatRepository.findById(id);
    }

    @Override
    public void addNewImage(Boat boat, Image image) {
        Set<Image> currentImages=boat.getImages();
        currentImages.add(image);
        boatRepository.save(boat);
    }

    @Override
    public List<Boat> findByOwnersId(Long id) {
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
    public boolean edit(Boat newBoat, Boolean deleteOldImages) {
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
        return true;
    }

    @Override
    public boolean delete(Long id) {
        Boat boat=boatRepository.findById(id);
        Set<AdditionalServices> additionalServices=boat.getAdditionalServices();
        Set<Image> images=boat.getImages();
        boatRepository.delete(boat);
        imageService.delete(images);
        additionalServicesService.delete(additionalServices);
        return true;
    }

    public List<Boat> findAll(){
        return boatRepository.findAll();
    }

    @Override
    public boolean canBeEditedOrDeleted(Long id) {
        LocalDateTime currentDate=LocalDateTime.now();
        if(boatReservationService.futureReservationsExist(currentDate,id)) return false;
        if(quickReservationBoatService.futureQuickReservationsExist(currentDate,id)) return false;
        return true;
    }

    @Override
    public double findAvgBoatRatingByOwnerId(Long ownerId) {
        boolean ratingExists=false;
        double count=0;
        double sum=0;
        for(Boat boat: boatRepository.findByOwnersId(ownerId) ){
            if(boat.getRating()!=0) {
                ratingExists=true;
                sum += boat.getRating();
                count++;
            }
        }
       if (ratingExists) return sum/count;
       return sum;
    }
}
