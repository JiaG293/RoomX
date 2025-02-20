package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.domain.employee.enums.PhoneType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "phones")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PhoneEntity {
    @Id
    @Column(name = "phone_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;
    @Enumerated(EnumType.STRING)
    @Column(name = "loai_so_dien_thoai", columnDefinition = "varchar(24)")
    PhoneType type;

    @Column(name = "so_dien_thoai", columnDefinition = "varchar(10)")
    String phone;

    @Column(name = "uu_tien", columnDefinition = "smallint")
    int priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    UserEntity user;

}
