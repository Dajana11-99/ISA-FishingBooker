package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("BOAT OWNER")
public class BoatOwner extends User {
    private static String roleApp = "ROLE_BOATOWNER";

    public BoatOwner() {
    }

    public BoatOwner(Long id, String name, String lastName, String email, String password, String phoneNum, Address address) {
        super(id, name, lastName, email, password, phoneNum, address);
    }

    public static String getRoleApp() {
        return roleApp;
    }

    public static void setRoleApp(String roleApp) {
        BoatOwner.roleApp = roleApp;
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
