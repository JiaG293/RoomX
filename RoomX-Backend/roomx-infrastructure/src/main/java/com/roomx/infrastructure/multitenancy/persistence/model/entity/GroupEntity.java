package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import com.roomx.domain.employee.enums.GroupScopeType;
import com.roomx.domain.employee.enums.GroupType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "groups")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GroupEntity {
    @Id
    @Column(name = "group_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    String id;

    @Column(name = "ten_nhom")
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "pham_vi_nhom")
    GroupScopeType groupScopeType;

    @Enumerated(EnumType.STRING)
    @Column(name = "loai_nhom")
    GroupType groupType;



    // RELATIONSHIP
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "group_users",  // Tên bảng n-n
            joinColumns = @JoinColumn(name = "group_id"),  // FK - GroupEntity
            inverseJoinColumns = @JoinColumn(name = "user_id")   // FK - UserEntity
    )
    Set<UserEntity> users = new HashSet<>();


}
