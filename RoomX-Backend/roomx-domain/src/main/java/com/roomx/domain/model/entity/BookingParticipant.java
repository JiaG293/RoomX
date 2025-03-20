package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.User;
import com.roomx.domain.model.vo.BookingParticipantId;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BookingParticipant {
    private BookingParticipantId id;
    private Booking booking;
    private User user;
}
