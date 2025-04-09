package com.roomx.domain.model.aggrerate;

import com.roomx.shared.enums.DeleteStatusType;
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
    private String name;
    private String code;
    private String layout;
    @Builder.Default
    private String status = DeleteStatusType.getDefaultString();
    @Builder.Default
    private String placeType = PlaceType.BRANCH.toString();


}
