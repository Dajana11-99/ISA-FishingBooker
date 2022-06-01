package rs.ac.uns.ftn.isa.fisherman.dto;
import java.time.LocalDateTime;
public class ComplaintDto {

    private Long id;
    private String text;
    private LocalDateTime date;
    private boolean responded;
    private String clientUsername;
    private String ownersUsername;
    private String complaintType;

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

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getOwnersUsername() {
        return ownersUsername;
    }

    public void setOwnersUsername(String ownersUsername) {
        this.ownersUsername = ownersUsername;
    }

    public String getComplaintType() {
        return complaintType;
    }

    public void setComplaintType(String complaintType) {
        this.complaintType = complaintType;
    }

    public ComplaintDto(Long id, String text, LocalDateTime date, boolean responded, String clientUsername, String ownersUsername, String complaintType) {
        this.id = id;
        this.text = text;
        this.date = date;
        this.responded = responded;
        this.clientUsername = clientUsername;
        this.ownersUsername = ownersUsername;
        this.complaintType = complaintType;
    }

    public ComplaintDto() {}
}
