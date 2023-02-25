package com.tp2.pry20220271.ulcernosis.resources.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeamWorkResource {
    private Long id;
    private Long medicId;
    private Long nurseId;
}
