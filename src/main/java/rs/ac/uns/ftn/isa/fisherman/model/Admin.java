package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    private transient final String role_app = "ROLE_ADMIN";
    private Boolean isPredefined;

    public Admin(){}
    public Admin(Long id, String name, String lastName, String email, String password, String phoneNum, Address address, Boolean isPredefined) {
        super(id, name, lastName, email, password, phoneNum, address);
        this.isPredefined = isPredefined;

    }

    @Override
    public String getRole_app() {
        return role_app;
    }


    public Boolean getPredefined() {
        return isPredefined;
    }

    public void setPredefined(Boolean predefined) {
        isPredefined = predefined;
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
