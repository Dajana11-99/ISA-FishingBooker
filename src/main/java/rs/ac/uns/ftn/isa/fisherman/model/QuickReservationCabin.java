package rs.ac.uns.ftn.isa.fisherman.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class QuickReservationCabin extends Reservation {

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="cabin_id")
    protected Cabin cabin;
    @Column(name="discount")
    protected Integer discount;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "cabin_quickr_services",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> addedAdditionalServices;

    public QuickReservationCabin(Long id, LocalDateTime startDate, LocalDateTime endDate, Client client, PaymentInformation paymentInformation,boolean ownerWroteAReport,String ownersUsername, Cabin cabin, Integer discount, Set<AdditionalServices> addedAdditionalServices) {
        super(id, startDate, endDate, client, paymentInformation,ownerWroteAReport,ownersUsername, false);
        this.cabin = cabin;
        this.discount = discount;
        this.addedAdditionalServices = addedAdditionalServices;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }

    public QuickReservationCabin(){}

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
