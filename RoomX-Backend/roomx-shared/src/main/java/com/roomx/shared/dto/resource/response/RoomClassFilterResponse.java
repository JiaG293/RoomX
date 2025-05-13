package com.roomx.shared.dto.resource.response;

import com.roomx.shared.dto.user.response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoomClassFilterResponse {
    private String id;
    private String roomClassCode;
    private int capacity;
    private RoomClassPriceHistoryResponse price;
    private List<String> nameEquipment;
    private List<String> nameService;
    private String status;

}
