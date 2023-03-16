package com.tp2.pry20220271.ulcernosis.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity(name = "diagnosis")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String stage1;

    @NotBlank
    private String stage2;

    @NotBlank
    private String stage3;

    @NotBlank
    @Column(nullable = false)
    private String stage4;
    @NotBlank
    @Column(nullable = false)
    private String stagePredicted;

    @Lob
    @JsonIgnore
    @Column(name = "ulcerPhoto", columnDefinition="LONGBLOB")
    private byte[] ulcerPhoto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    @NotNull(message = "Debe haber un paciente asignado para este diagnóstico")
    private Patient patient;

    @Column(nullable = false,name = "creator_id")
    private Long creatorId;

    @Enumerated(EnumType.STRING)
    @Column(name = "creator_type")
    private Type creatorType;

    @Temporal(TemporalType.DATE)
    @Column(name = "created_at")
    private Date createdAt;

    @PrePersist
    public void asignCreatedAt() {
        this.createdAt = new Date();
    }

}
