package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
public class Evaluation {

    @Id
    @SequenceGenerator(name = "period_sequence_generator", sequenceName = "period_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "period_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;
    @Column(name= "date")
    protected LocalDateTime date;
    @Column(name= "comment")
    protected String comment;
    @Column(name= "grade", nullable = false)
    protected Double grade;
    @Column(name= "approved")
    protected boolean approved;
    @ManyToOne(cascade=CascadeType.MERGE)
    @JoinColumn(name="users_id")
    protected Client client;

    public Evaluation(Long id, LocalDateTime date, String comment, Double grade, boolean approved, Client client) {
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.grade = grade;
        this.approved = approved;
        this.client = client;
    }

    public Evaluation(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getGrade() {
        return grade;
    }

    public void setGrade(Double grade) {
        this.grade = grade;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
