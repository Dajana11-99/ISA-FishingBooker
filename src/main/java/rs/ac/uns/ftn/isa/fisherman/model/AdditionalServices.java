package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.*;

@Entity
@Table(name="services")
public class AdditionalServices {
    @Id
    @SequenceGenerator(name = "cabin_sequence_generator", sequenceName = "cabin_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cabin_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;

    @Column(name="name")
    protected String name;
    @Column(name="price")
    protected double price;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public AdditionalServices(){}

    public AdditionalServices(Long id, String name, double price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }
}
