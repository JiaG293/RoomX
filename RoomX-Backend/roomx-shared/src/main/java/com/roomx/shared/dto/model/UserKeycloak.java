package com.roomx.shared.dto.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserKeycloak {
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;

}
