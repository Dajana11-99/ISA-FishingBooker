package rs.ac.uns.ftn.isa.fisherman.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="complaints")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "subject", discriminatorType = DiscriminatorType.STRING)
public abstract class Complaint {

    private static final String COMPLAINT_TYPE = "";
    @Id
    @SequenceGenerator(name = "account_sequence_generator", sequenceName = "account_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;
    @Version
    private int version;
    @Column(name="text")
    protected String text;
    @Column(name="date")
    protected LocalDateTime date;
    @Column(name="responded")
    protected boolean responded;
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="users_id")
    protected Client client;
    @Column(name="ownersUsername")
    protected String ownersUsername;


    public Complaint(Long id, String text, LocalDateTime date, boolean responded, Client client,String ownersUsername) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.responded = responded;
        this.client = client;
        this.ownersUsername = ownersUsername;
    }

    public Complaint(){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getOwnersUsername() {
        return ownersUsername;
    }

    public void setOwnersUsername(String ownersUsername) {
        this.ownersUsername = ownersUsername;
    }

    public  String getComplaintType() {
        return COMPLAINT_TYPE;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
