package rs.ac.uns.ftn.isa.fisherman.dto;


public class AddressDTO {
    private double longitude;
    private double latitude;
    private String country;
    private String city;
    private String streetAndNum;

    public AddressDTO() {}

    public AddressDTO(double longitude, double latitude, String country, String city, String streetAndNum) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.country = country;
        this.city = city;
        this.streetAndNum = streetAndNum;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetAndNum() {
        return streetAndNum;
    }

    public void setStreetAndNum(String streetAndNum) {
        this.streetAndNum = streetAndNum;
    }
}
