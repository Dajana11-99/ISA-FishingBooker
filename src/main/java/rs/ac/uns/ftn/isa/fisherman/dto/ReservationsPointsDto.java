package rs.ac.uns.ftn.isa.fisherman.dto;

public class ReservationsPointsDto {
    protected Long id;
    private Integer clientPoints;
    private Integer ownerPoints;
    private Integer appProfitPercentage;
    private Integer cancelationFeePercentage;

    public ReservationsPointsDto(Long id, Integer clientPoints, Integer ownerPoints, Integer appProfitPercentage, Integer cancelationFeePercentage ) {
        this.id = id;
        this.clientPoints = clientPoints;
        this.ownerPoints = ownerPoints;
        this.appProfitPercentage = appProfitPercentage;
        this.cancelationFeePercentage= cancelationFeePercentage;
    }

    public Integer getCancelationFeePercentage() {
        return cancelationFeePercentage;
    }

    public void setCancelationFeePercentage(Integer cancelationFeePercentage) {
        this.cancelationFeePercentage = cancelationFeePercentage;
    }

    public ReservationsPointsDto() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getClientPoints() {
        return clientPoints;
    }

    public void setClientPoints(Integer clientPoints) {
        this.clientPoints = clientPoints;
    }

    public Integer getOwnerPoints() {
        return ownerPoints;
    }

    public void setOwnerPoints(Integer ownerPoints) {
        this.ownerPoints = ownerPoints;
    }

    public Integer getAppProfitPercentage() {
        return appProfitPercentage;
    }

    public void setAppProfitPercentage(Integer appProfitPercentage) {
        this.appProfitPercentage = appProfitPercentage;
    }
}
