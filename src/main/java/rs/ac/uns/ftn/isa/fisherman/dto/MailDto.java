package rs.ac.uns.ftn.isa.fisherman.dto;

public class MailDto {
    private String response;
    private String recipient;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public MailDto(String response, String recipient) {
        this.response = response;
        this.recipient = recipient;
    }

    public MailDto() {
    }
}
