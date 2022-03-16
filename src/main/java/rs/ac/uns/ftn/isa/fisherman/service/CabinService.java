package rs.ac.uns.ftn.isa.fisherman.service;

import org.springframework.cache.annotation.Cacheable;
import rs.ac.uns.ftn.isa.fisherman.model.Cabin;
import rs.ac.uns.ftn.isa.fisherman.model.Image;

import java.util.List;
import java.util.Set;

public interface CabinService {

    Cabin findById(Long id);
    List<Cabin> findAll();
    @Cacheable("cabin")
    Cabin findByName(String cabin);
    void save(Cabin cabin);

    void addNewImage(String cabinName, Image image);

    Set<Cabin> findByOwnersId(Long id);

    void delete(Long id);

    void edit(Cabin cabin, Boolean deleteOldImages);
}
