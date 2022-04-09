package rs.ac.uns.ftn.isa.fisherman.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity

@DiscriminatorValue("InstructorPeriod")
public class AvailableInstructorPeriod extends AvailablePeriod {

    @ManyToOne
    @JoinColumn(name="users_id")
    protected FishingInstructor fishingInstructor;

    public AvailableInstructorPeriod() {

    }

    public AvailableInstructorPeriod(Long id, LocalDateTime startDate, LocalDateTime endDate, FishingInstructor fishingInstructor) {
        super(id, startDate, endDate);
        this.fishingInstructor = fishingInstructor;
    }

    public FishingInstructor getFishingInstructor() {
        return fishingInstructor;
    }

    public void setFishingInstructor(FishingInstructor fishingInstructor) {
        this.fishingInstructor = fishingInstructor;
    }
}
