package com.roomx.application.service.resource;

public record PlaceFilterRequest(
        String keyword,
        String searchBy,

        String status,

        String placeType

) {
}
