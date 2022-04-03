package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class QuickReservationCabin extends Reservation {
    @ManyToOne
    @JoinColumn(name="cabin_id")
    protected Cabin cabin;
    @Column(name="discount")
    protected Integer discount;

    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "cabin_quickr_services",
            joinColumns = @JoinColumn(name = "reservation_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> addedAdditionalServices;

    public QuickReservationCabin(Long id, LocalDateTime startDate, LocalDateTime endDate, Client client, PaymentInformation paymentInformation, Cabin cabin, Integer discount, Set<AdditionalServices> addedAdditionalServices) {
        super(id, startDate, endDate, client, paymentInformation);
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
