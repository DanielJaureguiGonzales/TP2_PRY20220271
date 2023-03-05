package com.tp2.pry20220271.ulcernosis.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "El nombre completo no debe estar vacío")
    @Column(nullable = false)
    private String fullName;

    @Email
    @NotEmpty(message = "El email no debe estar vacío")
    @Column(unique = true, nullable = false)
    private String email;

    @NotEmpty(message = "El DNI no debe estar vacío")
    @Column(unique = true, nullable = false, length = 8)
    private String dni;

    @NotEmpty(message = "El DNI no debe estar vacío")
    @Column(unique = true, nullable = false, length = 9)
    private String phone;

    @NotEmpty(message = "El nombre completo no debe estar vacío")
    @Column(nullable = false)
    private String civilStatus;

    @NotEmpty(message = "La contraseña no debe estar vacío")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Min(value = 18)
    @Max(value = 75)
    private Integer age;

    @NotEmpty(message = "La dirección no debe estar vacío")
    @Column(nullable = false)
    private String address;

    @Lob
    @JsonIgnore
    @Column(name = "avatar", columnDefinition="LONGBLOB")
    private byte[] avatar;

    @Enumerated(EnumType.STRING)
    private Rol role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

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
}
