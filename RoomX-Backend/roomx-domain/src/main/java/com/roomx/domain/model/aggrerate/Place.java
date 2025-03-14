package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.enums.PlaceType;
import lombok.*;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Place {
    private UUID id;
    private Branch branch;
    private String slug;
    private String floor;
    private String building;
    private String name;
    private String layout;
    @Builder.Default
    private String placeType = PlaceType.ROOM.toString();

    public String getPlaceType() {
        return placeType != null ? placeType : PlaceType.ROOM.toString();
    }

}
