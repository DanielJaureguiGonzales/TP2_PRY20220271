package com.tp2.pry20220271.ulcernosis.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "schedule")
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_in", nullable = false)
    private Date timeIn;

    @Column(name = "latitude")
    private String latitudeIn;

    @Column(name = "longitude")
    private String longitudeIn;

    @Column(name = "altitude")
    private String altitudeIn;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "time_out", nullable = false)
    private Date timeOut;

    @Column(name = "latitude")
    private String latitudeOut;

    @Column(name = "longitude")
    private String longitudeOut;

    @Column(name = "altitude")
    private String altitudeOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id",nullable = false)
    private Nurse nurse;

}
