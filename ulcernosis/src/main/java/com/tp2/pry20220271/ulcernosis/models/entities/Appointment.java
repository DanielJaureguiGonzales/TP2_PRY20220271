package com.tp2.pry20220271.ulcernosis.models.entities;


import com.tp2.pry20220271.ulcernosis.models.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "appointments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String address;

    private String dateAsigDiag;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long medicId;

    private Long nurseId;

    private Long diagnosisId;

    private Long patientId;
}
