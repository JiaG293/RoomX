package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.domain.employee.enums.UserType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Builder
@Entity
@Table(name = "users")
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    @Id
    @Column(name = "user_id")
    String id;

    @Column(name = "ma_nhan_vien", columnDefinition = "varchar(32)")
    String employeeId;

    @Column(name = "email", columnDefinition = "varchar(255)")
    String email;

    @Column(name = "ho", columnDefinition = "varchar(255)")
    String firstName;

    @Column(name = "ten", columnDefinition = "varchar(255)")
    String lastName;

    @Column(name = "gioi_tinh", columnDefinition = "boolean")
    boolean gender;

    @Column(name = "trang_thai", columnDefinition = "varchar(15)")
    boolean status;

    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Column(name = "danh_sach_so_dien_thoai")
    Set<PhoneEntity> phones = new HashSet<>();

    @Column(name = "phong_ban_id", columnDefinition = "varchar(32)")
    String departmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_nguoi_dung", columnDefinition = "varchar(32)")
    UserType userType;



    // RELATIONSHIP

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    Set<GroupEntity> groups = new HashSet<>();



}
