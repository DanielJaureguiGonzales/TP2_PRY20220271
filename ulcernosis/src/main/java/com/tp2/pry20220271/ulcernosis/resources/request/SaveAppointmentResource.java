package com.tp2.pry20220271.ulcernosis.resources.request;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SaveAppointmentResource {

       @NotEmpty(message = "La fecha de asignación no debe estar vacío")
       private String dateAsigDiag;
       private String address;
       private Long medicId;
       private Long nurseId;
       private Long patientId;
}
