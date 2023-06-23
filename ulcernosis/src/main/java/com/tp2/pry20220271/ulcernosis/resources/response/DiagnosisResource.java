package com.tp2.pry20220271.ulcernosis.resources.response;


import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DiagnosisResource {

    private Long id;
    private String stage1;
    private String stage2;
    private String stage3;
    private String stage4;
    private String stagePredicted;
    private Long patientId;
    private String patientName;
    private Long creatorId;
    private Type creatorType;
    private String creatorName;
    private Date createdAt;
    private Boolean isConfirmed;

}
