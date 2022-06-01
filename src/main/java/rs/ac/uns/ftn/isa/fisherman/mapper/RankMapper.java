package rs.ac.uns.ftn.isa.fisherman.mapper;

import rs.ac.uns.ftn.isa.fisherman.dto.RankDto;
import rs.ac.uns.ftn.isa.fisherman.model.Rank;

public class RankMapper {

    public Rank dtoToRank(RankDto rankDto){
        return  new Rank(rankDto.getId(),rankDto.getRank(),rankDto.getPoints(),rankDto.getDiscountPercentage());
    }
    public RankDto rankToDto(Rank rank){
        return new RankDto(rank.getId(),rank.getRankType(),rank.getPoints(),rank.getDiscountPercentage());
    }
}
