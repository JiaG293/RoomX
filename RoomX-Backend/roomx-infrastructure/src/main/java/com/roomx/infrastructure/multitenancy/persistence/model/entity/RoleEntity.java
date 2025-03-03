package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = RoleEntity.TABLE_NAME)
public class RoleEntity {
    public static final String TABLE_NAME = "role";
    public static final String COLUMN_ROLEID_NAME = "role_id";
    public static final String COLUMN_DESCRIPTION_NAME = "description";

    @Id
    @Size(max = 64)
    @Column(name = COLUMN_ROLEID_NAME, nullable = false, length = 64)
    private String roleId;

    @Column(name = COLUMN_DESCRIPTION_NAME, length = Integer.MAX_VALUE)
    private String description;


}