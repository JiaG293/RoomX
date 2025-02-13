package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meeting {
    private UUID meetingId;
    private String name;
    private Schedule schedule;
    private Location location;
    private List<Participant> participants;
    private String description;
}
