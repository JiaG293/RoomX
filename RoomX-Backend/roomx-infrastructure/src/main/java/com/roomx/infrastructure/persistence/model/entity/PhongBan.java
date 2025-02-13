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
@Table(name = "phong_ban", schema = "schema_roomx")
public class PhongBan {
    @Id
    @Column(name = "phong_ban_id", nullable = false)
    private UUID id;

    @Size(max = 128)
    @NotNull
    @Column(name = "ten", nullable = false, length = 128)
    private String ten;

    @Size(max = 256)
    @Column(name = "truong_phong", length = 256)
    private String truongPhong;

    @Column(name = "vi_tri", length = Integer.MAX_VALUE)
    private String viTri;

    @Column(name = "so_luong")
    private Short soLuong;

    @Size(max = 10)
    @Column(name = "so_dien_thoai", length = 10)
    private String soDienThoai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chi_nhanh_id")
    private ChiNhanh chiNhanh;

}*/
