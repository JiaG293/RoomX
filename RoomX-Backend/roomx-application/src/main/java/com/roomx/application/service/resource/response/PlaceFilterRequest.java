package com.roomx.application.service.resource.response;

public record PlaceFilterRequest(
        String keyword,
        String searchBy,

        String status,

        String placeType

) {
}
