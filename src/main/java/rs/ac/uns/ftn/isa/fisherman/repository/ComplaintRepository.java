package rs.ac.uns.ftn.isa.fisherman.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import rs.ac.uns.ftn.isa.fisherman.model.Complaint;
import java.util.List;

public interface ComplaintRepository  extends JpaRepository<Complaint, Long> {

    @Query(value="SELECT * FROM complaints where responded=false",nativeQuery = true)
    List<Complaint> getAll();

    @Query(value="SELECT * FROM complaints where id=:id",nativeQuery = true)
    Complaint getById(Long id);


}
