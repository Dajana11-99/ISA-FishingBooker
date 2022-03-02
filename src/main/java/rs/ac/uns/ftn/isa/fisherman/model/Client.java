package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENT")
public class Client extends User{

    private static String roleApp = "ROLE_CLIENT";

    public Client() {

    }

    public Client(Long id, String name, String lastName, String username, String password, String phoneNum, Address address) {
        super(id, name, lastName, username, password, phoneNum, address);
    }

    @Override
    public String getRoleApp() {
        return roleApp;
    }
}
