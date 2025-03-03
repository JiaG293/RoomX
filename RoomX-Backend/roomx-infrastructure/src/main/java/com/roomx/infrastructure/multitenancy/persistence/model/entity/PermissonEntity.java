package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = PermissonEntity.TABLE_NAME)
public class PermissonEntity {
    public static final String TABLE_NAME = "permisson";
    public static final String COLUMN_PERMISSIONID_NAME = "permission_id";
    public static final String COLUMN_DESCRIPTION_NAME = "description";

    @Id
    @Size(max = 64)
    @Column(name = COLUMN_PERMISSIONID_NAME, nullable = false, length = 64)
    private String permissionId;

    @Column(name = COLUMN_DESCRIPTION_NAME, length = Integer.MAX_VALUE)
    private String description;

}