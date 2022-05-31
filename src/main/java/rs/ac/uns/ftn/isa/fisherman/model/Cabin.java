package rs.ac.uns.ftn.isa.fisherman.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Cabin")
public class Cabin {
    @Id
    @SequenceGenerator(name = "cabin_sequence_generator", sequenceName = "cabin_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cabin_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;

    @Column(name="name", unique = true)
    protected String name;

    @Column(name="description")
    protected String description;

    @Column(name="numOfRooms")
    protected Integer numOfRooms;

    @Column(name="bedsPerRoom")
    protected Integer bedsPerRoom;

    @Column(name="cancelingConditions")
    protected String cancellingConditions;

    @Column(name="rules")
    protected String rules;

    @Column(name="price", nullable = false)
    protected double price;

    @Embedded
    protected  Address address;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "cabin_services",
            joinColumns = @JoinColumn(name = "cabin_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> additionalServices;

    @Column(name = "rating")
    protected Double rating;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "cabin_images",
            joinColumns = @JoinColumn(name = "cabin_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    @ManyToOne
    @JoinColumn(name = "users_id")
    protected CabinOwner cabinOwner;


    @OneToMany(mappedBy = "cabin", fetch = FetchType.EAGER, cascade = CascadeType.DETACH, orphanRemoval = true)
    private Set<AvailableCabinPeriod> availableCabinPeriods;

    @Version
    private  int version;

    public Cabin() {}

    public CabinOwner getCabinOwner() {
        return cabinOwner;
    }

    public void setCabinOwner(CabinOwner cabinOwner) {
        this.cabinOwner = cabinOwner;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double prices) {
        this.price = prices;
    }

    public Set<AdditionalServices> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalServices> additionalServices) {

        this.additionalServices = additionalServices;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    public Integer getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(Integer numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public Integer getBedsPerRoom() {
        return bedsPerRoom;
    }

    public void setBedsPerRoom(Integer bedsPerRoom) {
        this.bedsPerRoom = bedsPerRoom;
    }

    public Cabin(Long id, String name, String description, Integer numOfRooms, Integer bedsPerRoom, String rules, double price, Address address, Double rating, String cancellingConditions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numOfRooms = numOfRooms;
        this.bedsPerRoom = bedsPerRoom;
        this.rules = rules;
        this.price = price;
        this.address = address;
        this.rating = rating;
        this.additionalServices = new HashSet<>();
        this.images = new HashSet<>();
        this.availableCabinPeriods=new HashSet<>();
        this.cancellingConditions = cancellingConditions;
    }

    public Cabin(Long id, String name, String description, Integer numOfRooms, Integer bedsPerRoom, String rules, double price, Address address, Set<AdditionalServices> additionalServices, Double rating,String cancellingConditions) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.numOfRooms = numOfRooms;
        this.bedsPerRoom = bedsPerRoom;
        this.rules = rules;
        this.price = price;
        this.address = address;
        this.additionalServices = additionalServices;
        this.rating = rating;
        this.images=new HashSet<>();
        this.availableCabinPeriods=new HashSet<>();
        this.cancellingConditions = cancellingConditions;
    }

    public String getCancellingConditions() {
        return cancellingConditions;
    }

    public void setCancellingConditions(String cancelingConditions) {
        this.cancellingConditions = cancelingConditions;
    }
}
