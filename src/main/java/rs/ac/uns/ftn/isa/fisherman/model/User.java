package rs.ac.uns.ftn.isa.fisherman.model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import rs.ac.uns.ftn.isa.fisherman.enums.RankType;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="users")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role", discriminatorType = DiscriminatorType.STRING)
public abstract class User implements UserDetails {
    private static final String ROLE_APP = "";
    @Id
    @SequenceGenerator(name = "account_sequence_generator", sequenceName = "account_sequence", initialValue = 100)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "account_sequence_generator")
    @Column(name = "id", unique = true)
    protected Long id;
    @Version
    private int version;
    @Column(name="name")
    protected String name;
    @Column(name="lastName")
    protected String lastName;
    @Column(name="username", unique = true)
    protected String username;
    @Column(name="password", nullable = false)
    protected String password;
    @Column(name="phoneNum")
    protected String phoneNum;
    @Column(name="registrationReason")
    private String registrationReason;
    @Embedded
    private  Address address;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "activation_url", length = 64)
    private String activationURL;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @ManyToMany(fetch = FetchType.EAGER,cascade = {CascadeType.DETACH, CascadeType.REFRESH, CascadeType.MERGE})
    @JoinTable(name = "user_authority",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
    private List<Authority> authorities;

    @Column(name = "reasonForDeleting")
    private String reasonForDeleting;
    @Embedded
    private UserRank userRank;
    @Column(name = "rating")
    private Double rating;

    protected User(){}
    protected User(Long id, String name, String lastName, String username, String password, String phoneNum, Address address,String registrationReason) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.phoneNum = phoneNum;
        this.address = address;
        this.reasonForDeleting="";
        this.registrationReason= registrationReason;
        this.userRank = new UserRank(RankType.BRONZE,0);
        this.rating= 0.0;
    }

    public String getReasonForDeleting() {
        return reasonForDeleting;
    }

    public void setReasonForDeleting(String reasonForDeleting) {
        this.reasonForDeleting = reasonForDeleting;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRegistrationReason() {
        return registrationReason;
    }

    public void setRegistrationReason(String registrationReason) {
        this.registrationReason = registrationReason;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRoleApp() {
        return ROLE_APP;
    }

    public UserRank getUserRank() {
        return userRank;
    }

    public void setUserRank(UserRank userRank) {
        this.userRank = userRank;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public String getActivationURL() { return activationURL; }

    public void setActivationURL(String activationCode) { this.activationURL = activationCode;}

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }
}
