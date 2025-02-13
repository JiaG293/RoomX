package com.roomx.application.model;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {
    private String username;
    private String password;
}
