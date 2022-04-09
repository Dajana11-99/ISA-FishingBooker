package rs.ac.uns.ftn.isa.fisherman.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.repository.AdditionalServicesRepository;
import rs.ac.uns.ftn.isa.fisherman.service.AdditionalServicesService;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdditionalServicesServiceImpl implements AdditionalServicesService {
    @Autowired
    private AdditionalServicesRepository additionalServicesRepository;
    public List<AdditionalServices> getAll(){
        return additionalServicesRepository.findAll();
    }
    public AdditionalServices findById(Long id){
        return additionalServicesRepository.findById(id);
    }

    @Override
    public void delete (Set<AdditionalServices> oldAdditionalServices) {
        if(oldAdditionalServices.size()>0) {
            for (AdditionalServices additionalService : oldAdditionalServices)
                additionalServicesRepository.delete(additionalService);
        }
    }

    @Override
    public Set<AdditionalServices> findDeletedAdditionalServices(Set<AdditionalServices> oldServices,Set<AdditionalServices> newServices){
        Set<AdditionalServices> deletedServices=new HashSet<>();
        boolean exits=false;
        System.out.println("SIZE OD NOVE "+oldServices.size());
        System.out.println("SIZE OD stare "+newServices.size());
        for(AdditionalServices oldAdditionalService: oldServices) {
            for(AdditionalServices newAdditionalService: newServices){
                if (newAdditionalService.getId().equals(oldAdditionalService.getId()))
                    exits = true;
            }
            if(!exits)
                deletedServices.add(oldAdditionalService);
            exits=false;
        }
        return deletedServices;
    }
}
