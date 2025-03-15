package com.roomx.application.dto.user.request;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.Nulls;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserUpdateRequest {

    @NotBlank(message = "First name không được rỗng")
    private String firstName;

    @NotBlank(message = "Last name không được rỗng")
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại không hợp lệ (phải có 10 chữ số)")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String phoneNumber;

    @Email(message = "Email không hợp lệ")
    @NotBlank(message = "Email không được rỗng")
    private String email;

    private boolean gender;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String avatarImage;


}
