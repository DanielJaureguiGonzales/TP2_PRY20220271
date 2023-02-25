package com.tp2.pry20220271.ulcernosis.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;
import java.util.List;


@Entity(name = "medics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Medic {

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

    @NotEmpty(message = "El nombre completo no debe estar vacío")
    @Column(nullable = false)
    private String civilStatus;


    @Column(nullable = false)
    @Min(value = 18)
    @Max(value = 75)
    private Integer age;

    @NotEmpty(message = "La dirección no debe estar vacío")
    @Column(nullable = false)
    private String address;

    @NotEmpty(message = "El código del médico no debe estar vacío")
    @Column(nullable = false, length = 5, unique = true)
    private String cmp;

    @NotEmpty(message = "El nombre completo no debe estar vacío")
    @Column(nullable = false)
    private String password;

    @Lob
    @JsonIgnore
    @Column(name = "avatar", columnDefinition="LONGBLOB")
    private byte[] avatar;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "medic", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"medic"}, allowSetters = true)
    private List<Patient> patients;

    @OneToMany(mappedBy = "medic", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"medic"}, allowSetters = true)
    private List<TeamWork> teamWorks;

    @PrePersist
    public void asignCreatedAt(){
        this.createdAt = new Date();
    }


}
