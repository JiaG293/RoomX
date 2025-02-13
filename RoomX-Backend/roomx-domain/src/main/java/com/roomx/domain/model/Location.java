package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String name;
    private String address;
    private UUID roomId; // Optional, if meeting is in a specific room
    private String onlineMeetingLink; // Optional, if meeting is online
}
