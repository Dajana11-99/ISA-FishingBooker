package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User{

    private static String roleApp = "ROLE_CLIENT";
    private String registrationReason;
    public Client() {

    }

    public Client(Long id, String name, String lastName, String username, String password, String phoneNum, Address address ,String registrationReason) {
        super(id, name, lastName, username, password, phoneNum, address);
        this.registrationReason=registrationReason;
    }

    public String getRegistrationReason() {
        return registrationReason;
    }
    public void setRegistrationReason(String registrationReason) {
        this.registrationReason = registrationReason;
    }
    @Override
    public String getRoleApp() {
        return roleApp;
    }
}
