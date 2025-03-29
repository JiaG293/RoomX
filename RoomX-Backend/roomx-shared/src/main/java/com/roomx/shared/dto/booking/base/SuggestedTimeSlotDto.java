package com.roomx.shared.dto.booking.base;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuggestedTimeSlotDto {
    private List<String> timeMorning;
    private List<String> timeAfternoon;
}
