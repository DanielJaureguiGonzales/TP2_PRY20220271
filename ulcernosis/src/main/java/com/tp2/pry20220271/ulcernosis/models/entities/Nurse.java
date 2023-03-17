package com.tp2.pry20220271.ulcernosis.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.tp2.pry20220271.ulcernosis.models.enums.Rol;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;

@Entity(name = "nurses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Nurse {

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

    @NotEmpty(message = "El nombre completo no debe estar vacío")
    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer age;

    @NotEmpty(message = "La dirección no debe estar vacío")
    @Column(nullable = false)
    private String address;

    @Lob
    @JsonIgnore
    @Column(name = "avatar", columnDefinition="LONGBLOB")
    private byte[] avatar;

    @NotEmpty(message = "El código del enfermero no debe estar vacío")
    @Column(nullable = false, length = 6, unique = true)
    private String cep;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @Enumerated(EnumType.STRING)
    private Rol role;


    private Boolean haveTeamWork;

    @Column(nullable = false)
    private Boolean isAuxiliar;

    @Column(nullable = false)
    private Boolean itWasNotified;

    @OneToMany(mappedBy = "nurse", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"nurse"}, allowSetters = true)
    private List<TeamWork> teamWork;

    @OneToMany(mappedBy = "nurse", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"nurse"}, allowSetters = true)
    private List<Assignment> assignments;

    @OneToMany(mappedBy = "nurse", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"nurse"}, allowSetters = true)
    private List<Schedule> schedules;

    @PrePersist
    public void asignCreatedAt(){
        this.createdAt = new Date();
    }
}
