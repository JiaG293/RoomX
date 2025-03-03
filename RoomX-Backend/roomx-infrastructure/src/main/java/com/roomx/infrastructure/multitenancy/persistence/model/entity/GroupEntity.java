package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = GroupEntity.TABLE_NAME)
public class GroupEntity {
    public static final String TABLE_NAME = "\"group\"";
    public static final String COLUMN_ID_NAME = "group_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_GROUPTYPE_NAME = "group_type";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    private UUID id;

    @Size(max = 500)
    @Column(name = COLUMN_NAME_NAME, length = 500)
    private String name;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_GROUPTYPE_NAME, nullable = false, length = 32)
    private String groupType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

}