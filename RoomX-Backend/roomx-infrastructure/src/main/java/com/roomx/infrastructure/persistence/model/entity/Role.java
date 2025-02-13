/*
package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name = "role_id")
    String id;
    @Column(columnDefinition = "text")
    String description;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "role_permissions", // Tên bảng trung gian N-N
            joinColumns = @JoinColumn(name = "role_id"), // Cột liên kết từ bảng 1
            inverseJoinColumns = @JoinColumn(name = "permission_id") // Cột liên kết từ bảng N
    )
    Set<Permission> permissions;
}
*/
