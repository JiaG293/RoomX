package com.roomx.controller.resource;

import com.roomx.application.service.admin.KeycloakAppService;
import com.roomx.shared.dto.booking.request.BookingRequestApprovalRequest;
import com.roomx.shared.dto.booking.response.BookingRequestResponse;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/keycloak")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class KeycloakController {
    KeycloakAppService keycloakAppService;

    @PostMapping
    public ResultResponse<?> getPageBookingRequest(
            @RequestParam(name = "realmName") String realmName
    ) {
        var result = keycloakAppService.initRealmNewUser(realmName);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }
}
