package com.roomx.controller.resource;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/demo")
public class DemoController {

    @GetMapping("/public")
    public String publicEndpoint() {
        return "Public Endpoint";
    }

    @GetMapping("/private")
    @PreAuthorize("hasRole('ADMIN')")
    public String protectedEndpoint() {
        return "Protected Endpoint";
    }
}
