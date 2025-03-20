package com.roomx.domain.model.aggrerate;

import com.roomx.shared.enums.RoomStatusType;
import lombok.*;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class Room {
    private UUID id;
    private String roomCode;
    @Builder.Default
    private String status = RoomStatusType.AVAILABLE.toString();
    private String description;
    private Place place;
    private RoomClass roomClass;


}
