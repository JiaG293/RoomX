/*
package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "chi_nhanh", schema = "schema_roomx")
public class ChiNhanh {
    @Id
    @Column(name = "chi_nhanh_id", nullable = false)
    private UUID id;

    @Size(max = 500)
    @Column(name = "ten", length = 500)
    private String ten;

    @Size(max = 500)
    @Column(name = "so_dien_thoai", length = 500)
    private String soDienThoai;

    @Size(max = 500)
    @Column(name = "email", length = 500)
    private String email;

    @Size(max = 500)
    @Column(name = "dia_chi", length = 500)
    private String diaChi;

}*/
