package rs.ac.uns.ftn.isa.fisherman.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class CabinReservation extends Reservation{


    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="cabin_id")
    protected Cabin cabin;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "cabin_reservation_services",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> addedAdditionalServices;

    public CabinReservation(Long id, LocalDateTime startDate, LocalDateTime endDate, Client client, PaymentInformation paymentInformation,boolean ownerWroteAReport,String ownersUsername, Cabin cabin, Set<AdditionalServices> addedAdditionalServices, boolean evaluated) {
        super(id, startDate, endDate, client, paymentInformation, ownerWroteAReport, ownersUsername, evaluated);
        this.cabin = cabin;
        this.addedAdditionalServices = addedAdditionalServices;
    }

    public CabinReservation(Cabin cabin, Set<AdditionalServices> addedAdditionalServices) {
        this.cabin = cabin;
        this.addedAdditionalServices = addedAdditionalServices;
    }


    public CabinReservation(){}

    public Cabin getCabin() {
        return cabin;
    }

    public void setCabin(Cabin cabin) {
        this.cabin = cabin;
    }

    public Set<AdditionalServices> getAddedAdditionalServices() {
        return addedAdditionalServices;
    }

    public void setAddedAdditionalServices(Set<AdditionalServices> addedAdditionalServices) {
        this.addedAdditionalServices = addedAdditionalServices;
    }
}
