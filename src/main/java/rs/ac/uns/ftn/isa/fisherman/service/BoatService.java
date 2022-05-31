package rs.ac.uns.ftn.isa.fisherman.service;
import org.springframework.cache.annotation.Cacheable;
import rs.ac.uns.ftn.isa.fisherman.model.AdditionalServices;
import rs.ac.uns.ftn.isa.fisherman.model.Boat;
import rs.ac.uns.ftn.isa.fisherman.model.Image;
import java.util.List;
import java.util.Set;

public interface BoatService {
    void save(Boat boat);

    boolean addNewBoat(Boat boat,Set<AdditionalServices> services);

    Boat findById(Long id);

    void addNewImage(Boat boat, Image image);

    List<Boat> findByOwnersId(Long id);

    Boat findByName(String name);

    Boat findByNameAndOwner(String boatName, Long boatOwner);

    boolean edit(Boat boat, Boolean deleteOldImages) throws Exception;

    boolean delete(Long id);

    List<Boat> findAll();

    boolean canBeEditedOrDeleted(Long id);

    double findAvgBoatRatingByOwnerId(Long ownerId);
}
