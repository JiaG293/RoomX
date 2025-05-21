package com.roomx.shared.dto.resource.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomMaxMinResponse {
    private Integer max;
    private Integer min;
}
