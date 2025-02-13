package com.roomx.infrastructure.test;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
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
