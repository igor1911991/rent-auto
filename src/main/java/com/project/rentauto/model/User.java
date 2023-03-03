package com.project.rentauto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    @Email(message = "invalid email")
    private String email;

    @Column(name = "phone")
    @Pattern(regexp = "^(8\\d{3}\\d{7})$",  message = "8(999)000000")
    private String phone;

    @Column(name = "drivers_license_number")
    @Pattern(regexp = "^(\\d{2}\\w{2}\\d{6})$",  message = "00 AA 000000")
    private String driversLicenseNumber;

    @Column(name= "password")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    public String getFormattedPhoneNumber() {
        String formattedPhoneNumber;
        String p = String.valueOf(this.phone);
        formattedPhoneNumber = String.format("%s(%s)%s", p.charAt(0), p.substring(1, 4), p.substring(4));
        return formattedPhoneNumber;
    }
}