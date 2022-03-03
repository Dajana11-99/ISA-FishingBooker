package rs.ac.uns.ftn.isa.fisherman.dto;

import java.util.HashSet;
import java.util.Set;

public class FishingInstructorDto {

    private Long id;

    private String username;

    private String password;

    private String firstname;

    private String lastname;

    private String phoneNum;
    private AddressDTO address;
    private String registrationReason;
    private String role;
    private double rating;
    private Set<AvailableInstructorPeriodDto> availableInstructorPeriodDtoSet;

    public FishingInstructorDto(Long id, String username, String password, String firstname, String lastname, String phoneNum, AddressDTO address, String registrationReason, String role, double rating) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNum = phoneNum;
        this.address = address;
        this.registrationReason = registrationReason;
        this.role = role;
        this.rating = rating;
        this.availableInstructorPeriodDtoSet = new HashSet<>();
    }

    public FishingInstructorDto(Long id, String username, String password, String firstname, String lastname, String phoneNum, AddressDTO address, String registrationReason, String role, double rating, Set<AvailableInstructorPeriodDto> availableInstructorPeriodDtoSet) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNum = phoneNum;
        this.address = address;
        this.registrationReason = registrationReason;
        this.role = role;
        this.rating = rating;
        this.availableInstructorPeriodDtoSet = availableInstructorPeriodDtoSet;
    }

    public FishingInstructorDto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public String getRegistrationReason() {
        return registrationReason;
    }

    public void setRegistrationReason(String registrationReason) {
        this.registrationReason = registrationReason;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public Set<AvailableInstructorPeriodDto> getAvailableInstructorPeriodDtoSet() {
        return availableInstructorPeriodDtoSet;
    }

    public void setAvailableInstructorPeriodDtoSet(Set<AvailableInstructorPeriodDto> availableInstructorPeriodDtoSet) {
        this.availableInstructorPeriodDtoSet = availableInstructorPeriodDtoSet;
    }
}
