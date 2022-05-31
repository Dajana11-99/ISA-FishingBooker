package rs.ac.uns.ftn.isa.fisherman.service;

import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.model.Adventure;
import rs.ac.uns.ftn.isa.fisherman.model.Image;
import java.util.List;
import java.util.Set;

public interface AdventureService {
    void save(Adventure adventure);
    Adventure findByName(String adventureName);
    void addNewImage(String adventureName, Image image);
    Set<Adventure> findAdventuresByInstructorId(Long id);
    Adventure findAdventureByName(String adventureName, Long fishingInstructorId);
    void delete(Long id);
    boolean edit(Adventure adventure, Long id) throws Exception;
    boolean canBeEditedOrDeleted(Long id);
    List<Adventure> findAll();
    boolean addNewAdventure(Adventure adventure,Set<AdditionalServices>additionalServices);
    Adventure findById(Long id);
    List<Adventure> findAdventuresByInstructorIds(List<Long> instructorIds);
}
