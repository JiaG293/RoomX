package com.roomx.domain.employee.model;


import com.roomx.domain.employee.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private String id;
    private String employeeId;
    private String email;
    private String departmentId;
    private String firstName;
    private String lastName;
    private boolean gender;
    private boolean status;
    private UserType userType;
    private Set<Phone> phone = new HashSet<>();

}


