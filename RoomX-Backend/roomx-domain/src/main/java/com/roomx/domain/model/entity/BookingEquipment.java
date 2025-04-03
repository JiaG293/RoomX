package com.roomx.domain.model.entity;

import com.roomx.domain.model.aggrerate.Booking;
import com.roomx.domain.model.aggrerate.Equipment;
import com.roomx.domain.model.aggrerate.Service;
import com.roomx.domain.model.vo.BookingEquipmentId;
import com.roomx.domain.model.vo.BookingServiceId;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode
public class BookingEquipment {
    private BookingEquipmentId id;
    private Booking booking;
    private Equipment equipment;

    public BookingEquipmentId getId(){
        if(booking != null && equipment != null){
            new BookingEquipmentId(booking.getId(), equipment.getId());
        }
        return this.id;
    }
}
