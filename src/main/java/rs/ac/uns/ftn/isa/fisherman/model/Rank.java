package rs.ac.uns.ftn.isa.fisherman.model;
import rs.ac.uns.ftn.isa.fisherman.enums.RankType;

import javax.persistence.*;

@Entity
public class Rank {
    @Id
    @SequenceGenerator(name = "rank_sequence_generator", sequenceName = "rank_sequence", initialValue = 4)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rank_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;
    @Enumerated(EnumType.ORDINAL)
    private RankType rankType;
    private Integer points;
    private Integer discountPercentage;


    public Rank(Long id, RankType rankType, Integer points, Integer discountPercentage) {
        this.id = id;
        this.rankType = rankType;
        this.points = points;
        this.discountPercentage = discountPercentage;
    }

    public Rank() {}

    public RankType getRankType() {
        return rankType;
    }

    public void setRankType(RankType rank) {
        this.rankType = rank;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Long getId() {
        return id;
    }

    public Integer getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(Integer discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
