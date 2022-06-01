package rs.ac.uns.ftn.isa.fisherman.model;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
public abstract class AvailablePeriod {
    @Id
    @SequenceGenerator(name = "period_sequence_generator", sequenceName = "period_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "period_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;
    @Column(name= "startDate")
    private LocalDateTime startDate;
    @Column(name= "endDate")
    private LocalDateTime endDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public AvailablePeriod(Long id, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public AvailablePeriod() {}
}
