package com.tp2.pry20220271.ulcernosis.resources.response;

import com.tp2.pry20220271.ulcernosis.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MedicResource {
    private Long id;
    private String fullName;
    private String email;
    private String dni;
    private String phone;
    private Integer age;
    private String address;
    private String cmp;
    private String civilStatus;
    private Role role;
    private Date createdAt;
}
