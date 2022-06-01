package rs.ac.uns.ftn.isa.fisherman.model;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
@Entity
@Table(name="Boat")
public class Boat {
    @Id
    @SequenceGenerator(name = "boat_sequence_generator", sequenceName = "boat_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boat_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;

    @Column(name="name")
    protected String name;

    @Column(name="type")
    protected String type;

    @Column(name="length")
    protected double length;

    @Column(name="engineCode")
    protected String engineCode;

    @Column(name="enginePower")
    protected String enginePower;

    @Column(name="maxSpeed")
    protected String maxSpeed;

    @Column(name="navigationEquipment")
    protected String navigationEquipment;


    @Embedded
    protected  Address address;

    @Column(name="description")
    protected String description;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(
            name = "boat_images",
            joinColumns = @JoinColumn(name = "boat_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    @Column(name="maxPeople")
    protected Integer maxPeople;

    @Column(name="rules")
    protected String rules;

    @Column(name="fishingEquipment")
    protected String fishingEquipment;

    @Column(name="price")
    protected double price;

    @Column(name="rating")
    protected double rating;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "boat_services",
            joinColumns = @JoinColumn(name = "boat_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> additionalServices;

    @Column(name="cancelingCondition")
    protected String cancelingCondition;

    @ManyToOne
    @JoinColumn(name = "users_id")
    protected BoatOwner boatOwner;

    @Version
    private  int version;

    public Boat(){}

    public Boat(Long id, String name, String type, double length, String engineCode, String enginePower, String maxSpeed, String navigationEquipment, Address address, String description, Integer maxPeople, String rules, String fishingEquipment, double price, double rating, String cancelingCondition) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.length = length;
        this.engineCode = engineCode;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.navigationEquipment = navigationEquipment;
        this.address = address;
        this.description = description;
        this.maxPeople = maxPeople;
        this.rules = rules;
        this.fishingEquipment = fishingEquipment;
        this.price = price;
        this.rating = rating;
        this.cancelingCondition = cancelingCondition;
        this.additionalServices=new HashSet<>();
        this.images=new HashSet<>();
        this.availableBoatPeriods=new HashSet<>();
    }
    @OneToMany(mappedBy = "boat", fetch = FetchType.EAGER, cascade = CascadeType.DETACH,orphanRemoval = true)
    private Set<AvailableBoatPeriod> availableBoatPeriods;
    public Boat(Long id, String name, String type, double length, String engineCode, String enginePower, String maxSpeed, String navigationEquipment, Address address, String description, Integer maxPeople, String rules, String fishingEquipment, double price, double rating, String cancelingCondition,Set<AdditionalServices> additionalServices) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.length = length;
        this.engineCode = engineCode;
        this.enginePower = enginePower;
        this.maxSpeed = maxSpeed;
        this.navigationEquipment = navigationEquipment;
        this.address = address;
        this.description = description;
        this.maxPeople = maxPeople;
        this.rules = rules;
        this.fishingEquipment = fishingEquipment;
        this.price = price;
        this.rating = rating;
        this.cancelingCondition = cancelingCondition;
        this.additionalServices=additionalServices;
        this.images=new HashSet<>();
        this.availableBoatPeriods=new HashSet<>();
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public BoatOwner getBoatOwner() {
        return boatOwner;
    }

    public void setBoatOwner(BoatOwner boatOwner) {
        this.boatOwner = boatOwner;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getLength() {
        return length;
    }

    public void setLength(double length) {
        this.length = length;
    }

    public String getEngineCode() {
        return engineCode;
    }

    public void setEngineCode(String engineCode) {
        this.engineCode = engineCode;
    }

    public String getEnginePower() {
        return enginePower;
    }

    public void setEnginePower(String enginePower) {
        this.enginePower = enginePower;
    }

    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getNavigationEquipment() {
        return navigationEquipment;
    }

    public void setNavigationEquipment(String navigationEquipment) {
        this.navigationEquipment = navigationEquipment;
    }


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Image> getImages() {
        return images;
    }

    public void setImages(Set<Image> images) {
        this.images = images;
    }

    public Integer getMaxPeople() {
        return maxPeople;
    }

    public void setMaxPeople(Integer maxPeople) {
        this.maxPeople = maxPeople;
    }

    public String getRules() {
        return rules;
    }

    public void setRules(String rules) {
        this.rules = rules;
    }

    public String getFishingEquipment() {
        return fishingEquipment;
    }

    public void setFishingEquipment(String fishingEquipment) {
        this.fishingEquipment = fishingEquipment;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Set<AdditionalServices> getAdditionalServices() {
        return additionalServices;
    }

    public void setAdditionalServices(Set<AdditionalServices> additionalServices) {
        this.additionalServices = additionalServices;
    }

    public String getCancelingCondition() {
        return cancelingCondition;
    }

    public void setCancelingCondition(String cancelingCondition) {
        this.cancelingCondition = cancelingCondition;
    }
}
