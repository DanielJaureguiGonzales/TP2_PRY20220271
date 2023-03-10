package com.tp2.pry20220271.ulcernosis.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "time_in")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeIn;

    @Column(name = "latitude_in")
    private double latitudeIn;

    @Column(name = "longitude_in")
    private double longitudeIn;

    @Column(name = "time_out")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime timeOut;

    @Column(name = "latitude_out")
    private double latitudeOut;

    @Column(name = "longitude_out")
    private double longitudeOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id",nullable = false)
    private Nurse nurse;

}
