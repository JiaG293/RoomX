package com.roomx.domain.model.aggrerate;

import com.roomx.shared.enums.PlaceType;
import lombok.*;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Place {
    private UUID id;
    private UUID parentId;
    private Branch branch;
    private String name;
    private String code;
    private String layout;
    private String status;
    @Builder.Default
    private String placeType = PlaceType.BRANCH.toString();


    public String getPlaceTypeDefault() {
        return placeType != null ? placeType : PlaceType.BRANCH.toString();
    }

}
