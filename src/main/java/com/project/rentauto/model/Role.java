package com.project.rentauto.model;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_name")
    private String roleName;

    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<User> users;

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    @Override
    public String toString() {
        return roleName;
    }
}
