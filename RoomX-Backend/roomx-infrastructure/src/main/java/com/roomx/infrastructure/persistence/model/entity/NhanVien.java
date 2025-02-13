/*
package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "nhan_vien", schema = "schema_roomx", uniqueConstraints = {
        @UniqueConstraint(name = "unq_nhan_vien_ma_nhan_vien", columnNames = {"ma_nhan_vien"}),
        @UniqueConstraint(name = "unq_nhan_vien_email", columnNames = {"email"})
})
public class NhanVien {
    @Id
    @Column(name = "nhan_vien_id", nullable = false)
    private UUID id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "nhan_vien_id", nullable = false)
    private NhanVien nhanVien;

    @Size(max = 32)
    @NotNull
    @Column(name = "ma_nhan_vien", nullable = false, length = 32)
    private String maNhanVien;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "phong_ban_id", nullable = false)
    private PhongBan phongBan;

    @Size(max = 128)
    @Column(name = "ho", length = 128)
    private String ho;

    @Size(max = 64)
    @Column(name = "ten", length = 64)
    private String ten;

    @Size(max = 10)
    @Column(name = "so_dien_thoai", length = 10)
    private String soDienThoai;

    @Size(max = 512)
    @Column(name = "email", length = 512)
    private String email;

    @Column(name = "gioi_tinh")
    private Boolean gioiTinh;

    @Size(max = 64)
    @Column(name = "chuc_vu", length = 64)
    private String chucVu;

}*/
