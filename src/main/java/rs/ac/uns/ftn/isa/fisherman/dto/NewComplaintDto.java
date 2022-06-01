package rs.ac.uns.ftn.isa.fisherman.dto;

public class NewComplaintDto {
    private String text;
    private String clientUsername;
    private String subjectRole;
    private Long reservationId;

    public NewComplaintDto(String text, String clientUsername, String subjectRole, Long reservationId) {
        this.text = text;
        this.clientUsername = clientUsername;
        this.subjectRole = subjectRole;
        this.reservationId = reservationId;
    }

    public NewComplaintDto(){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getSubjectRole() {
        return subjectRole;
    }

    public void setSubjectRole(String subjectRole) {
        this.subjectRole = subjectRole;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
}
