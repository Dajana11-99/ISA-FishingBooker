package rs.ac.uns.ftn.isa.fisherman.dto;

import java.util.Objects;

public class LogInDto {
    private String email;
    private String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LogInDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public LogInDto() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LogInDto logInDto = (LogInDto) o;
        return Objects.equals(email, logInDto.email) && Objects.equals(password, logInDto.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email, password);
    }

    @Override
    public String toString() {
        return "LogInDto{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
