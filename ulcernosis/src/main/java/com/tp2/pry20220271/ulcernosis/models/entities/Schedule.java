package com.tp2.pry20220271.ulcernosis.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tp2.pry20220271.ulcernosis.models.enums.TypeHour;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "schedules")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "time")
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime time;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Enumerated(EnumType.STRING)
    private TypeHour typeHour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id",nullable = false)
    private Nurse nurse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;
}
