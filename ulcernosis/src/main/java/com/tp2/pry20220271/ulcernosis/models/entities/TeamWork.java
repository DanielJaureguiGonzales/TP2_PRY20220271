package com.tp2.pry20220271.ulcernosis.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity(name = "team_works")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medic_id",nullable = false)
    private Medic medic;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nurse_id",nullable = false)
    private Nurse nurse;
}
