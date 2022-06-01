package rs.ac.uns.ftn.isa.fisherman.service;
import rs.ac.uns.ftn.isa.fisherman.enums.RankType;
import rs.ac.uns.ftn.isa.fisherman.model.Rank;
import java.util.List;

public interface RankService {
    List<Rank> getAll();
    void update(Rank rank);
    Integer getPointsByRank(Integer rank);
    Integer getDiscountByRank(Integer rank);
    RankType updateRankType(Integer points);

}
