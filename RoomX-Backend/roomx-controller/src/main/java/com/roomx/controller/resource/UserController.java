package com.roomx.controller.resource;

import com.roomx.application.dto.request.UserCreationRequest;
import com.roomx.application.dto.response.UserResponse;
import com.roomx.application.exception.ResultResponse;
import com.roomx.application.model.UserKeycloak;
import com.roomx.application.model.UserModel;
import com.roomx.application.service.admin.impl.KeycloakUserService;
import com.roomx.application.service.auth.impl.UserServiceImpl;
import com.roomx.controller.model.request.UserRequestModel;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserServiceImpl userService;
    KeycloakUserService keycloakUserService;

    @GetMapping("/info")
    public ResultResponse<?> getCurrentUser() {
        return ResultResponse.<UserModel>builder().result(userService.getUser()).build();
    }

    @PreAuthorize("HAS_ROLE_SUPER_ADMIN")
    @PostMapping
    public ResultResponse<?> createUser(@RequestBody UserCreationRequest request) {
        log.info("request:  {}", request);
        return ResultResponse.<UserResponse>builder().result(keycloakUserService.createUser(request)).build();
    }

    @GetMapping
    public Map<String, Object> getAuthInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaims();
        }

        return Map.of("message", "No JWT found");
    }

    @GetMapping("/roles")
    public Collection<String> getRoles() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ADMIN') or #id == #jwt.subject")
    @PostMapping("/check/{id}")
    public ResultResponse<?> check(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Map<String, String> response = new HashMap<>();
        response.put("subject", id);
        response.put("role", "ff");

        return ResultResponse.<Map<String, String>>builder().result(response).build();
    }

}
