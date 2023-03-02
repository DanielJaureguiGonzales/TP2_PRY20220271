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

@Entity(name = "patients")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Patient {

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

    @Column(nullable = false)
    @Min(value = 0)
    @Max(value = 75)
    private Integer age;

    @NotEmpty(message = "La dirección no debe estar vacío")
    @Column(nullable = false)
    private String address;

    @NotEmpty(message = "El nombre completo no debe estar vacío")
    @Column(nullable = false)
    private String civilStatus;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medic_id",nullable = false)
    @NotNull(message = "Debe haber un médico asignado para este paciente")
    private Medic medic;

    @Lob
    @JsonIgnore
    @Column(name = "avatar", columnDefinition="LONGBLOB")
    private byte[] avatar;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @OneToMany(mappedBy = "patient", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"patient"}, allowSetters = true)
    private List<Diagnosis> diagnosis;

    @OneToMany(mappedBy = "patient", fetch=FetchType.LAZY,cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonIgnoreProperties(value = {"patient"}, allowSetters = true)
    private List<Assignment> assignments;

    @PrePersist
    public void asignCreatedAt(){
        this.createdAt = new Date();
    }
}
