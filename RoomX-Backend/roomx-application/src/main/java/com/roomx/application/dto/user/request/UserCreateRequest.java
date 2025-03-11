package com.roomx.application.dto.user.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserCreateRequest {
    @NotBlank(message = "User code không được rỗng")
    private String userCode;

    @NotBlank(message = "First name không được rỗng")
    private String firstName;

    @NotBlank(message = "Last name không được rỗng")
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại không hợp lệ (phải có 10 chữ số)")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String phoneNumber;

    @Size(min = 8, message = "Mật khẩu phải có ít nhất 8 ký tự")
    private String password;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được rỗng")
    private String email;

    private boolean gender;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String avatarImage;


    @Pattern(regexp = "^(PARTNER|EMPLOYEE)$", message = "valid.user.create.type")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String type;


    @JsonSetter(nulls = Nulls.AS_EMPTY)

    private Set<String> roles;
}
