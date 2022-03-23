package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.User;
import java.util.List;

public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUsername(String username );


    @Query(value = "SELECT * FROM users WHERE role != 'ADMIN'  AND enabled=false AND accepted=false",nativeQuery = true)
    List<User> getNewUsers();

    @Query(value = "SELECT role FROM users WHERE id=:id",nativeQuery = true)
    String findRoleById(@Param("id")Long id);


    @Query(value = "SELECT * FROM users WHERE role != 'ADMIN' AND reason_for_deleting!=''",nativeQuery = true)
    List<User> getReguestsForDeletingAccount();


}
