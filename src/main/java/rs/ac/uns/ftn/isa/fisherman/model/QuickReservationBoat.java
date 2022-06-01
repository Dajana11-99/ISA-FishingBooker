package rs.ac.uns.ftn.isa.fisherman.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
public class QuickReservationBoat extends Reservation{

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="boat_id")
    protected Boat boat;

    @Column(name="discount")
    protected Integer discount;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "boat_quickr_services",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> addedAdditionalServices;

    protected boolean needsCaptainServices;
    public QuickReservationBoat(){}

    public QuickReservationBoat(Long id, LocalDateTime startDate, LocalDateTime endDate, Client client, PaymentInformation paymentInformation,boolean ownerWroteAReport,String ownersUsername, Boat boat, Integer discount, Set<AdditionalServices> addedAdditionalServices, boolean needsCaptainServices) {
        super(id, startDate, endDate, client, paymentInformation,ownerWroteAReport,ownersUsername, false);
        this.boat = boat;
        this.discount = discount;
        this.addedAdditionalServices = addedAdditionalServices;
        this.needsCaptainServices = needsCaptainServices;
    }

    public QuickReservationBoat(Long id, LocalDateTime startDate, LocalDateTime endDate, Client client) {
        this.id = id;
        this.setStartDate(startDate);
        this.setEndDate(endDate);
        this.client = client;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public boolean getNeedsCaptainServices() {
        return needsCaptainServices;
    }

    public void setNeedsCaptainServices(boolean needsCaptainServices) {
        this.needsCaptainServices = needsCaptainServices;
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
