package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.model.Image;
import rs.ac.uns.ftn.isa.fisherman.repository.CabinRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AdditionalServicesService;
import rs.ac.uns.ftn.isa.fisherman.service.CabinService;
import rs.ac.uns.ftn.isa.fisherman.service.ImageService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CabinServiceImpl implements CabinService {
    @Autowired
    private CabinRepository cabinRepository;
    @Autowired
    private AdditionalServicesService additionalServicesService;
    @Autowired
    private ImageService imageService;

    public Cabin findById(Long id){
        return cabinRepository.findById(id);
    }
    public List<Cabin> findAll(){
        return cabinRepository.findAll();
    }
    @Override
    public Cabin findByName(String cabin) {
        return cabinRepository.findByName(cabin);
    }

    @Override
    public void save(Cabin cabin) {
        cabinRepository.save(cabin);
    }

    @Override
    public void addNewImage(String cabinName, Image image) {
        Cabin cabin= cabinRepository.findByName(cabinName);
        Set<Image> currentImages=cabin.getImages();
        currentImages.add(image);
        cabinRepository.save(cabin);
    }

    @Override
    public Set<Cabin> findByOwnersId(Long id) {
        return cabinRepository.findByOwnersId(id);
    }

    @Override
    public void delete(Long id) {
        Cabin cabin=cabinRepository.findById(id);
        Set<AdditionalServices> additionalServices=cabin.getAdditionalServices();
        Set<Image> images=cabin.getImages();
        cabinRepository.delete(cabin);
        additionalServicesService.delete(additionalServices);
        imageService.delete(images);
    }

    @Override
    public void edit(Cabin newCabin, Boolean deleteOldImages) {
        Cabin oldCabin=this.cabinRepository.findByName(newCabin.getName());
        oldCabin.setAddress(newCabin.getAddress());
        oldCabin.setPrice(newCabin.getPrice());
        oldCabin.setNumOfRooms(newCabin.getNumOfRooms());
        oldCabin.setBedsPerRoom(newCabin.getBedsPerRoom());
        oldCabin.setDescription(newCabin.getDescription());
        oldCabin.setRules(newCabin.getRules());
        Set<AdditionalServices> oldAdditionalServices=oldCabin.getAdditionalServices();
        Set<Image> oldImages= oldCabin.getImages();
        oldCabin.setAdditionalServices(newCabin.getAdditionalServices());
        if(Boolean.TRUE.equals(deleteOldImages))  oldCabin.setImages(new HashSet<>());
        cabinRepository.save(oldCabin);
        Set<AdditionalServices> savedServices= cabinRepository.findByName(oldCabin.getName()).getAdditionalServices();
        if(Boolean.TRUE.equals(deleteOldImages))   imageService.delete(oldImages);
        additionalServicesService.delete(additionalServicesService.findDeletedAdditionalServices(oldAdditionalServices,savedServices));
    }

}
