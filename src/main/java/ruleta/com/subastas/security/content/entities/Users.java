package ruleta.com.subastas.security.content.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, unique = true, nullable = false)
    private String username;

    @Column(length = 200, nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled = true;

    @Column(length = 50, nullable = false)
    private String name;

    @Column(length = 50, nullable = false)
    private String lastName;

    @Column(length = 20, nullable = false)
    private String phone; // WhatsApp

    @Column(length = 50, nullable = false)
    private String city; // Procedencia

    @Column(nullable = true)
    private String resetPasswordCode;

    @Column(nullable = true)
    private LocalDateTime resetPasswordCodeExpiration;

    @OneToMany(
            mappedBy = "user",
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Role> roles;

    public Users() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResetPasswordCode() {
        return resetPasswordCode;
    }

    public void setResetPasswordCode(String resetPasswordCode) {
        this.resetPasswordCode = resetPasswordCode;
    }

    public LocalDateTime getResetPasswordCodeExpiration() {
        return resetPasswordCodeExpiration;
    }

    public void setResetPasswordCodeExpiration(LocalDateTime resetPasswordCodeExpiration) {
        this.resetPasswordCodeExpiration = resetPasswordCodeExpiration;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
        for (Role role : roles) {
            role.setUser(this);
        }
    }
}
