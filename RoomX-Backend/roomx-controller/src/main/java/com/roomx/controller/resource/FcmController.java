package com.roomx.controller.resource;

import com.roomx.application.service.notification.FcmTokenAppService;
import com.roomx.shared.exception.api.ResultResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/fcm")
@OpenAPIDefinition(info = @Info(title = "Firebase token", version = "v1", description = "Using firebase messaging cloud for notification with webapp and android app"))
public class FcmController {
    FcmTokenAppService fcmTokenAppService;


    @PostMapping("/{token}")
    public ResultResponse<?> sendToken(@PathVariable String token) {
        fcmTokenAppService.addFcmToken(token);
        return ResultResponse.<Void>builder().build();
    }

    @DeleteMapping("/{token}")
    public ResultResponse<?> removeToken(@PathVariable String token) {
        fcmTokenAppService.removeFcmToken(token);
        return ResultResponse.<Void>builder().build();
    }
}
