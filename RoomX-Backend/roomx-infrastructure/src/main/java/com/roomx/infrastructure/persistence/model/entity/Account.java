/*
package com.roomx.infrastructure.persistence.model.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "accounts")
@EntityListeners(AuditingEntityListener.class)
public class Account {
    @Id
    @Column(name = "account_id")
    String id;

    @Column(unique = true, nullable = false)
    String email;

    @Column(nullable = false)
    String password;

    Boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "account_roles", // Tên bảng trung gian n-n
            joinColumns = @JoinColumn(name = "account_id"), // Cột liên kết từ bảng 1
            inverseJoinColumns = @JoinColumn(name = "role_id") // Cột liên kết từ bảng N
    )
    Set<Role> roles;


    //TIMESTAMP
    @CreatedDate
    @Column(updatable = false)
    LocalDateTime createdAt;

    @LastModifiedDate
//    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        isActive = true;
    }
}
*/
