package ruleta.com.subastas.security.content.dto;

import java.time.LocalDateTime;
import java.util.List;

public class UserDTO {

    private Long id;
    private String username;
    private String password;
    private String name;
    private String lastName;
    private String phone;
    private String city;
    private List<String> roles;
    private String resetPasswordCode;
    private LocalDateTime resetPasswordCodeExpiration;

    public UserDTO() {
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

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
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
}
