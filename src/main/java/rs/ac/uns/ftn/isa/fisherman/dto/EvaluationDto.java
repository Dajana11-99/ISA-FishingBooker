package rs.ac.uns.ftn.isa.fisherman.dto;

import java.time.LocalDateTime;

public class EvaluationDto {
    protected Long id;
    protected LocalDateTime date;
    protected String comment;
    protected Double grade;
    protected boolean approved;
    protected String clientUsername;

    public EvaluationDto(Long id, LocalDateTime date, String comment, Double grade, boolean approved, String clientUsername) {
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.grade = grade;
        this.approved = approved;
        this.clientUsername = clientUsername;
    }
    public EvaluationDto(){}

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

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }
}
