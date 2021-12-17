package rs.ac.uns.ftn.isa.fisherman.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FISHING INSTRUCTOR")
public class FishingInstructor extends  User {

    private static String roleApp = "ROLE_FISHING_INSTRUCTOR";

    public FishingInstructor() {
    }

    public FishingInstructor(Long id, String name, String lastName, String email, String password, String phoneNum, Address address) {
        super(id, name, lastName, email, password, phoneNum, address);
    }

    @Override
    public String getRoleApp() {
        return roleApp;
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
