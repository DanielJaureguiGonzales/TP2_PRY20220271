package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.enums.Status;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AppointmentResource {
    private Long id;
    private String dateAsigDiag;
    private Status status;
    private String address;
    private Long medicId;
    private Long nurseId;
    private Long diagnosisId;
    private Long patientId;
}
