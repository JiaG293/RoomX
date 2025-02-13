package com.roomx.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private UUID roomId;
    private String name;
    private int capacity;
    private String location;
    private List<Equipment> equipment;
    private List<Service> services;
}
