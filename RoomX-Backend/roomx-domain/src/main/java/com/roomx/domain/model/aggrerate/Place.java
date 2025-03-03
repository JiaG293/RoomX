package com.roomx.domain.model.aggrerate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Place {
    private UUID id;
    private Branch branch;
    private String slug;
    private Short floor;
    private Short building;
    private String name;
    private String layout;
    private String placeType;



}
