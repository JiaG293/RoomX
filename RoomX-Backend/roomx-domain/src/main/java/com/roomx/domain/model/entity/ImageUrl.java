package com.roomx.domain.model.entity;

import lombok.*;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class ImageUrl {
    private UUID id;

    private String type;

    private String url;

    private Integer imageOrder;
}
