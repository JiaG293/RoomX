package com.roomx.application.dto.response;

import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String maNhanVien;
    private String matKhau;
    private String tenNhanVien;
    private String email;
}
