package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinOwner;
import rs.ac.uns.ftn.isa.fisherman.model.FishingInstructor;

import java.util.List;

public interface FishingInstructorRepository extends JpaRepository<FishingInstructor,Integer> {
    @Query(value="SELECT role,id,activation_url,city,country,latitude,longitude,street_and_num,username,rating,enabled,last_name,last_password_reset_date,name,password,phone_num,registration_reason,is_predefined FROM users where role='FISHING INSTRUCTOR' and enabled=false",nativeQuery = true)
    List<FishingInstructor> getNewFishingInstructor();

    @Query(value="SELECT role,id,activation_url,city,country,latitude,longitude,street_and_num,username,rating,enabled,last_name,last_password_reset_date,name,password,phone_num,registration_reason,is_predefined FROM users where role='FISHING INSTRUCTOR' and enabled=true",nativeQuery = true)
    List<FishingInstructor> getActiveFishingInstructor();

    @Query(value="SELECT role,id,activation_url,city,country,latitude,longitude,street_and_num,username,rating,enabled,last_name,last_password_reset_date,name,password,phone_num,registration_reason,is_predefined FROM users where username=:username",nativeQuery = true)
    FishingInstructor findByUsername(@Param("username")String username );
}
