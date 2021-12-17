package rs.ac.uns.ftn.isa.fisherman.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FISHING INSTRUCTOR")
public class FishingInstructor extends  User {

    private transient final String Role = "ROLE_FISHING_INSTRUCTOR";

    public FishingInstructor() {
    }

    public FishingInstructor(Long id, String name, String lastName, String email, String password, String phoneNum, Address address) {
        super(id, name, lastName, email, password, phoneNum, address);
    }

    @Override
    public String getRole() {
        return Role;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }
}
