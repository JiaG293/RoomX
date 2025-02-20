/*
package com.roomx.infrastructure.multitenancy.test;

import com.roomx.infrastructure.multitenancy.security.context.TenantContextHolder;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DataInitializer {

    InstrumentRepository instrumentRepository;


    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        // Dữ liệu cho người thuê tên: giau
        TenantContextHolder.setTenantIdentifier("giau");
        if (instrumentRepository.count() == 0) {
            instrumentRepository.saveAll(List.of(
                    Instrument.builder().type("piano").name("Steinway").build(),
                    Instrument.builder().type("string").name("cello").build(),
                    Instrument.builder().type("guitar").name("Gibson Firebird").build()
            ));
        }
        TenantContextHolder.clear();


        // Dữ liệu cho người thuê tên: sang
        TenantContextHolder.setTenantIdentifier("sang");
        if (instrumentRepository.count() == 0) {
            instrumentRepository.saveAll(List.of(
                    Instrument.builder().type("organ").name("Hammond B3").build(),
                    Instrument.builder().type("string").name("Viola").build(),
                    Instrument.builder().type("guitar").name("Gibson Firebird (Fake)").build()
            ));
        }

        TenantContextHolder.clear();
    }



}
*/
