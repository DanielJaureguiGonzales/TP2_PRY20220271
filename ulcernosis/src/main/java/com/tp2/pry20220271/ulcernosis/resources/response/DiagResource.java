package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.enums.Type;
import lombok.Data;

@Data
public class DiagResource {
    private String stage_1;
    private String stage_2;
    private String stage_3;
    private String stage_4;
    private String stage_predicted;
    private Type creatorType;

}
