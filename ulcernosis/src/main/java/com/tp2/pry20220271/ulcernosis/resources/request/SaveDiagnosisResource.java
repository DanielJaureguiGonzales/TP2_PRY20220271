package com.tp2.pry20220271.ulcernosis.resources.request;


import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SaveDiagnosisResource {

    private byte[] ulcerPhoto;

    @NotNull(message = "El id del paciente no puede ser vacío")
    private Long patientId;

    @NotNull(message = "El id del enfermero no puede ser vacío")
    private Long creatorId;

    @Enumerated(EnumType.STRING)
    private Type creatorType;

}
