package com.tp2.pry20220271.ulcernosis.resources.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tp2.pry20220271.ulcernosis.models.entities.Patient;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveDiagnosisResource {

    private byte[] ulcerPhoto;

    private Long patientId;

    private Long creatorId;

}
