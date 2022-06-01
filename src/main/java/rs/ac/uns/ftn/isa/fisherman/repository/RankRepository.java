package rs.ac.uns.ftn.isa.fisherman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import rs.ac.uns.ftn.isa.fisherman.model.Rank;

public interface RankRepository extends JpaRepository<Rank,Long> {

    @Query(value="SELECT points FROM rank where rank=:rank  ",nativeQuery = true)
    Integer getPointsByRank(@Param("rank")Integer rank);

    @Query(value="SELECT discount_percentage FROM rank where rank=:rank  ",nativeQuery = true)
    Integer getDiscountByRank(@Param("rank") Integer rank);

}
