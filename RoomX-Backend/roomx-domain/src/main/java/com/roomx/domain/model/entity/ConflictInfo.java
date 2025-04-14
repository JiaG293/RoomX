package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ConflictInfo {
    private BookingRequest request;
    @Builder.Default
    private List<BookingRequest> conflictingRequests = new ArrayList<>();
}
