package com.tp2.pry20220271.ulcernosis.resources.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveAssignmentResource {

    private Long patientId;
    private Long nurseId;
}
