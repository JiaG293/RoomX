package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;


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
    public static final String COLUMN_LEVEL_NAME = "level";
    public static final String JOINTABLE_USERS_NAME = "user_role";
    public static final String JOINCOLUMNS_JOINCOLUMN_USERS_NAME = "role_id";
    public static final String INVERSEJOINCOLUMNS_JOINCOLUMN_USERS_NAME = "user_id";

    @Id
    @Size(max = 64)
    @Column(name = COLUMN_ROLEID_NAME, nullable = false, length = 64)
    private String roleId;

    @Column(name = COLUMN_DESCRIPTION_NAME, length = Integer.MAX_VALUE)
    private String description;

    @Column(name = COLUMN_LEVEL_NAME, length = Integer.MAX_VALUE)
    private int level;

    @ManyToMany
    @JoinTable(name = JOINTABLE_USERS_NAME,
            joinColumns = @JoinColumn(name = JOINCOLUMNS_JOINCOLUMN_USERS_NAME),
            inverseJoinColumns = @JoinColumn(name = INVERSEJOINCOLUMNS_JOINCOLUMN_USERS_NAME))
    private Set<UserEntity> users = new HashSet<>();


}