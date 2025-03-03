package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.BookingRequest;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.vo.BookingRequestParticipantId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BookingRequestParticipant {
    private BookingRequestParticipantId id;
    private BookingRequest bookingRequest;
    private User user;
}
