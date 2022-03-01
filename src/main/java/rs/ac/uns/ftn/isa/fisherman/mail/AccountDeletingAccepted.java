package rs.ac.uns.ftn.isa.fisherman.mail;

public class AccountDeletingAccepted implements MailFormatter<String> {

    @Override
    public String getText(String reason) {
        return "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "    <title>Document</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <h1>Hello!</h1>\n" +
                "    <h2>Your request for deleting account has been accepted. Reason:  " + reason+"</h2>\n" +
                "    <br>\n" +
                "    <h2>Sincerely,</h2>\n" +
                "    <h2>Fisherman team.</h2>\n" +
                "\n" +
                "</body>\n" +
                "</html>";
    }


    @Override
    public String getSubject() {
        return "Fisherman - account deletion accepted";
    }

}
