package rs.ac.uns.ftn.isa.fisherman.dto;


import java.time.LocalDateTime;

public class ReservationDto {

    protected Long id;
    protected LocalDateTime startDate;
    protected LocalDateTime endDate;
    protected Double price;
    protected String clientUsername;
    protected String clientFullName;
    private boolean evaluated;

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public ReservationDto(Long id, LocalDateTime startDate, LocalDateTime endDate, Double price, String clientUsername, String clientFullName) {
        this.id = id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.price = price;
        this.clientUsername = clientUsername;
        this.clientFullName = clientFullName;
    }

    public ReservationDto(){}

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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getClientUsername() {
        return clientUsername;
    }

    public void setClientUsername(String clientUsername) {
        this.clientUsername = clientUsername;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }
}
