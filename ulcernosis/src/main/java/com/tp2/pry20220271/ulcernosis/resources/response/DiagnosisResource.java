package com.tp2.pry20220271.ulcernosis.resources.response;


import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DiagnosisResource {

    private Long id;

    private String stage1;

    private String stage2;

    private String stage3;

    private String stage4;

    private String stagePredicted;

    private byte[] ulcerPhoto;

    private Long patientId;

    private Long creatorId;
    private Type creatorType;
}