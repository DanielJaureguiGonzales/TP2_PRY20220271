package com.tp2.pry20220271.ulcernosis.resources.request;


import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

    @Enumerated(EnumType.STRING)
    private Type creatorType;

}
