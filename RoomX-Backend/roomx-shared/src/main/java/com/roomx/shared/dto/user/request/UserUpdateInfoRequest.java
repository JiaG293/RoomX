package com.roomx.shared.dto.user.request;

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
public class UserUpdateInfoRequest {
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String firstName;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String lastName;

    @Pattern(regexp = "^\\d{10}$", message = "Số điện thoại không hợp lệ (phải có 10 chữ số)")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String phoneNumber;

    @Email(message = "Email không hợp lệ")
    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String email;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private Boolean gender;

    @JsonSetter(nulls = Nulls.AS_EMPTY)
    private String avatarImage;
}
