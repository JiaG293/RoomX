package com.roomx.infrastructure.multitenancy.persistence.model.entity;

import jakarta.persistence.*;
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
@Table(name = ImageUrlEntity.TABLE_NAME)
public class ImageUrlEntity {
    public static final String TABLE_NAME = "image_url";
    public static final String COLUMN_ID_NAME = "image_url_id";
    public static final String COLUMN_TYPE_NAME = "type";
    public static final String COLUMN_URL_NAME = "url";
    public static final String COLUMN_IMAGEORDER_NAME = "image_order";


    @Id
    @Column(name = COLUMN_ID_NAME, nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Size(max = 64)
    @Column(name = COLUMN_TYPE_NAME, length = 64)
    private String type;

    @Column(name = COLUMN_URL_NAME, length = Integer.MAX_VALUE)
    private String url;

    @Column(name = COLUMN_IMAGEORDER_NAME)
    private Integer imageOrder;

}
