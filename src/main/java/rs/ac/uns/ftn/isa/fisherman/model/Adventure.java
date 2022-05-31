package rs.ac.uns.ftn.isa.fisherman.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Adventure {

    @Id
    @SequenceGenerator(name = "adventure_sequence_generator", sequenceName = "adventure_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "adventure_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;

    @Column(name="name")
    protected String name;

    @Embedded
    protected  Address address;

    @Column(name="description")
    protected String description;


    @Column(name="instructorsBiography")
    protected String instructorsBiography;


    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "adventure_images",
            joinColumns = @JoinColumn(name = "adventure_id"),
            inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<Image> images;

    @Column(name="maxPeople")
    protected  Integer maxPeople;

    @Column(name="price")
    protected double price;

    @Column(name="rules")
    protected String rules;

    @Column(name="equipment")
    protected String equipment;

    @ManyToMany(fetch = FetchType.EAGER,cascade=CascadeType.ALL)
    @JoinTable(name = "adventure_services",
            joinColumns = @JoinColumn(name = "adventure_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "service_id", referencedColumnName = "id"))
    protected Set<AdditionalServices> additionalServices;

    @Column(name="cancelingCondition")
    protected String cancelingCondition;

    @ManyToOne
    @JoinColumn(name = "users_id")
    protected  FishingInstructor fishingInstructor;

    @Version
    private  int version;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getInstructorsBiography() {
        return instructorsBiography;
    }

    public void setInstructorsBiography(String instructorsBiography) {
        this.instructorsBiography = instructorsBiography;
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

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
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

    public Adventure(Long id, String name, Address address, String description, String instructorsBiography, Integer maxPeople, double price, String rules, String equipment, String cancelingCondition) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.instructorsBiography = instructorsBiography;
        this.maxPeople = maxPeople;
        this.price = price;
        this.rules = rules;
        this.equipment = equipment;
        this.cancelingCondition = cancelingCondition;
        this.additionalServices= new HashSet<>();
        this.images = new HashSet<>();


    }

    public Adventure(Long id, String name, Address address, String description, String instructorsBiography, Set<Image> images, Integer maxPeople, double price, String rules, String equipment, Set<AdditionalServices> additionalServices, String cancelingCondition) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.description = description;
        this.instructorsBiography = instructorsBiography;
        this.images = images;
        this.maxPeople = maxPeople;
        this.price = price;
        this.rules = rules;
        this.equipment = equipment;
        this.additionalServices = additionalServices;
        this.cancelingCondition = cancelingCondition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Adventure() {
    }

    public FishingInstructor getFishingInstructor() {
        return fishingInstructor;
    }

    public void setFishingInstructor(FishingInstructor fishingInstructor) {
        this.fishingInstructor = fishingInstructor;
    }
}
