package rs.ac.uns.ftn.isa.fisherman.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("BOATEVALUATION")
public class BoatEvaluation extends Evaluation {

    private static final String TYPE = "BOAT EVALUATION";
    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.REFRESH})
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name= "boat_id")
    private Boat boat;

    public BoatEvaluation(Long id, LocalDateTime date, String comment, Double grade, boolean approved, Client client,String ownersUsername, Boat boat) {
        super(id, date, comment, grade, approved, client,ownersUsername);
        this.boat = boat;
    }

    public BoatEvaluation() {}

    public Boat getBoat() {
        return boat;
    }

    public void setBoat(Boat boat) {
        this.boat = boat;
    }

    public  String getType() {
        return TYPE;
    }
}
