package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.vo.BookingEquipmentId;
import com.roomx.domain.model.vo.BookingServiceId;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BookingService {
    private BookingServiceId id;
    private Booking booking;
    private Service service;

    public BookingServiceId getId(){
        if(booking != null && service != null){
            new BookingServiceId(booking.getId(), service.getId());
        }
        return this.id;
    }
}
