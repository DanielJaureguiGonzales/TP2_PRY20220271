package com.tp2.pry20220271.ulcernosis.models.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiagResults {
    private String stage_1;
    private String stage_2;
    private String stage_3;
    private String stage_4;
    private String stage_predicted;
}
