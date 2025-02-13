package com.roomx.domain.model;

import lombok.*;
import lombok.experimental.FieldDefaults;



@Builder
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDomain {
    final String maNhanVien;
    String email;
    String password;

    public boolean isValidEmail() {
        return email != null && email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");
    }

    @Override
    public String toString() {
        return "UserDomain(maNhanVien=" + maNhanVien + ", email=" + email + ", password=******)";
    }

}
