package com.tp2.pry20220271.ulcernosis.resources.etc;

import com.tp2.pry20220271.ulcernosis.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseId {
    private Long id;
    private Role type;
}
