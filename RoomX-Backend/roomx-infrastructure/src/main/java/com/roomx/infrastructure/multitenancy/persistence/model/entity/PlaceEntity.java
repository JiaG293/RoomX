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
    public static final String COLUMN_SLUG_NAME = "slug";
    public static final String COLUMN_FLOOR_NAME = "floor";
    public static final String COLUMN_BUILDING_NAME = "building";
    public static final String COLUMN_NAME_NAME = "name";
    public static final String COLUMN_LAYOUT_NAME = "layout";
    public static final String COLUMN_PLACETYPE_NAME = "place_type";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "branch_id")
    private BranchEntity branch;

    @Column(name = COLUMN_SLUG_NAME)
    private String slug;

    @Column(name = COLUMN_FLOOR_NAME)
    private String floor;

    @Column(name = COLUMN_BUILDING_NAME)
    private String building;

    @Column(name = COLUMN_NAME_NAME)
    private String name;

    @Column(name = COLUMN_LAYOUT_NAME)
    private String layout;

    @Size(max = 32)
    @NotNull
    @Column(name = COLUMN_PLACETYPE_NAME, nullable = false, length = 32)
    private String placeType;

}