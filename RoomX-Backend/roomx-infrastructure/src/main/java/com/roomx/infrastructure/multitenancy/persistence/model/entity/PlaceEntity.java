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
@Table(name = PlaceEntity.TABLE_NAME)
public class PlaceEntity {
    public static final String TABLE_NAME = "place";
    public static final String COLUMN_ID_NAME = "place_id";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_LAYOUT_NAME = "layout";
    public static final String COLUMN_PLACETYPE_NAME = "place_type";
    public static final String COLUMN_PARENTID_NAME = "parent_id";
    public static final String COLUMN_STATUS_NAME = "status";
    public static final String COLUMN_CODE_NAME = "code";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = COLUMN_NAME_NAME, length = Integer.MAX_VALUE)
    private String name;

    @Column(name = COLUMN_LAYOUT_NAME, length = Integer.MAX_VALUE)
    private String layout;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_PLACETYPE_NAME, nullable = false, length = 32)
    private String placeType;

    @Column(name = COLUMN_PARENTID_NAME)
    private UUID parentId;

    @Size(max = 32)
    @Column(name = COLUMN_CODE_NAME)
    private String code;


    @Size(max = 32)
    @Column(name = COLUMN_STATUS_NAME, length = 32)
    private String status;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

}