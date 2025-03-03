package com.roomx.domain.model.aggrerate;

import com.roomx.domain.model.enums.RoomStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Room {
    private UUID id;
    private Place place;

    @Builder.Default
    private String status = RoomStatusType.AVAILABLE.toString();
    private String description;
    private String roomCode;
    private RoomClass roomClass;


}
