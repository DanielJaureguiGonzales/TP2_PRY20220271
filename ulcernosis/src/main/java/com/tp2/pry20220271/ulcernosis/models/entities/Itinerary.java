package com.tp2.pry20220271.ulcernosis.models.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "itineraries")
public class Itinerary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "time_in")
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime timeIn;
    @Column(name = "time_out")
    @JsonFormat(pattern = "hh:mm a")
    private LocalTime timeOut;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id",nullable = false)
    private Nurse nurse;
}
