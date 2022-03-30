package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class BoatReservation extends Reservation{
    @ManyToOne
    @JoinColumn(name="boat_id")
    protected Boat boat;

    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "boat_reservation_services",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> addedAdditionalServices;

    protected  boolean needsCaptainService;

    public BoatReservation(){};

    public BoatReservation(Long id, LocalDateTime startDate, LocalDateTime endDate, Double price, Client client, Boat boat,Set<AdditionalServices> addedAdditionalServices,boolean needsCaptainService) {
        super(id, startDate, endDate, price, client);
        this.boat = boat;
        this.addedAdditionalServices = addedAdditionalServices;
        this.needsCaptainService=needsCaptainService;
    }

    public boolean getNeedsCaptainService() {
        return needsCaptainService;
    }

    public void setNeedsCaptainService(boolean needsCaptainService) {
        this.needsCaptainService = needsCaptainService;
    }

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public Set<AdditionalServices> getAddedAdditionalServices() {
        return addedAdditionalServices;
    }

    public void setAddedAdditionalServices(Set<AdditionalServices> addedAdditionalServices) {
        this.addedAdditionalServices = addedAdditionalServices;
    }
}
