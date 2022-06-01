package rs.ac.uns.ftn.isa.fisherman.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@DiscriminatorValue("CABINOWNER")
public class CabinOwner extends User{
    private static final String ROLE_APP = "ROLE_CABINOWNER";

    @OneToMany(mappedBy = "cabinOwner", fetch = FetchType.LAZY, cascade = CascadeType.DETACH, orphanRemoval = true)
    private Set<Cabin> cabins;

    @OneToMany(mappedBy = "cabinOwner", fetch = FetchType.EAGER, cascade = CascadeType.DETACH, orphanRemoval = true)
    private Set<AvailableCabinPeriod> availableCabinPeriods;

    public CabinOwner(){}
    public CabinOwner(Long id, String name, String lastName, String username, String password, String phoneNum, Address address,String registrationReason) {
        super(id, name, lastName, username, password, phoneNum, address,registrationReason);
        this.availableCabinPeriods= new HashSet<>();
    }
    @Override
    public String getRoleApp() {
        return ROLE_APP;
    }

    public Set<Cabin> getCabins() {
        return cabins;
    }

    public void setCabins(Set<Cabin> cabins) {
        this.cabins = cabins;
    }
}
