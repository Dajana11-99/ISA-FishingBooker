package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@DiscriminatorValue("BOATOWNER")
public class BoatOwner extends User {
    private static final String ROLE_APP = "ROLE_BOATOWNER";

    @OneToMany(mappedBy = "boatOwner",   cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE}, orphanRemoval = true)
    private Set<Boat> boats;


    public BoatOwner() {}

    public BoatOwner(Long id, String name, String lastName, String username, String password, String phoneNum, Address address,String registrationReason) {
        super(id, name, lastName, username, password, phoneNum, address,registrationReason);
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    @Override
    public String getRoleApp() {
        return ROLE_APP;
    }




}
