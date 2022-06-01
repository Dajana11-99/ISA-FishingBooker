package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.CabinSubscription;
import java.util.Set;

public interface CabinSubscriptionRepository extends JpaRepository<CabinSubscription, Long> {

    @Query(value="SELECT * FROM cabin_subscription where users_id=:user_id",nativeQuery = true)
    Set<CabinSubscription> getClientSubscriptions(@Param("user_id")Long usersId);

    @Query(value="SELECT CASE WHEN  COUNT(c) > 0 THEN true ELSE false END FROM cabin_subscription c where cabin_id=:cabin_id and users_id=:users_id",nativeQuery = true)
    boolean subscriptionExists(@Param("cabin_id")Long cabinId,@Param("users_id")Long usersId);

    @Query(value="SELECT * FROM cabin_subscription where users_id=:users_id and cabin_id=:cabin_id",nativeQuery = true)
    CabinSubscription getSubscriptionOnCabin(@Param("cabin_id")Long cabinId,@Param("users_id")Long usersId);

    @Query(value="SELECT u.username FROM cabin_subscription c join users u on c.users_id=u.id where cabin_id=:cabin_id",nativeQuery = true)
    Set<String> findCabinSubscribers(@Param("cabin_id")Long cabinId);
}
