package com.roomx.infrastructure.multitenancy.persistence.model.dto;

import java.util.UUID;

public record PlaceDto(UUID id, String name, String layout, String placeType, UUID parentId, String code, String status, UUID branchId) {}

