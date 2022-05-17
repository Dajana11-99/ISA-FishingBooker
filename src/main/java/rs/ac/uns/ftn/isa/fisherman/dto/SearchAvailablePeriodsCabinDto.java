package rs.ac.uns.ftn.isa.fisherman.dto;

import java.time.LocalDateTime;

public class SearchAvailablePeriodsCabinDto extends SearchAvailablePeriodsDto {

    private int numberOfRooms;
    private int bedsPerRoom;

    public SearchAvailablePeriodsCabinDto(LocalDateTime startDate, LocalDateTime endDate, double price, String username, double rating, String streetAndNum, String city, String country, int numberOfRooms, int bedsPerRoom) {
        super(startDate, endDate, price, username, rating, streetAndNum, city, country);
        this.numberOfRooms = numberOfRooms;
        this.bedsPerRoom = bedsPerRoom;
    }

    public SearchAvailablePeriodsCabinDto() {}

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(int numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    public int getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(int bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
    }
}
