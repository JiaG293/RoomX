/*
package com.roomx.infrastructure.multitenancy.test;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/v1/instrument")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class InstrumentController {


    InstrumentRepository instrumentRepository;

    @GetMapping
    List<Instrument> getInstruments() {

        return instrumentRepository.findAll();
    }

    @GetMapping("/add")
    void addInstruments() {
    instrumentRepository.save(Instrument.builder().name("hello").type("ok").build());

    }
}
*/
