package com.roomx.application.service.event.impl;


import com.roomx.application.service.event.EventAppService;
import org.springframework.stereotype.Service;

@Service
public class EventAppServiceImpl implements EventAppService {
    @Override
    public String testApplication(String text) {
        return text;
    }
}
