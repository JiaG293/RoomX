package com.roomx.domain.repository;


import com.roomx.domain.model.entity.BookingEquipment;
import com.roomx.domain.model.vo.BookingEquipmentId;

import java.util.List;
import java.util.Optional;

public interface BookingEquipmentRepository {
    Optional<BookingEquipment> findById(BookingEquipmentId bookingEquipmentId);
    Optional<BookingEquipment> findByBookingId(String bookinId);
    Optional<BookingEquipment> findByEquipmentId(String serviceId);
    List<BookingEquipment> findAllByBookingId(String bookingId);
    BookingEquipment save(BookingEquipment bookingEquipment);
    List<BookingEquipment> saveAll(List<BookingEquipment> listBookingEquipment );
}
