package com.roomx.controller.resource;

import com.roomx.shared.dto.user.request.UserCreateRequest;
import com.roomx.shared.dto.user.request.UserQueryFilterRequest;
import com.roomx.shared.dto.user.response.UserCreateResponse;
import com.roomx.shared.dto.user.response.UserInfoReponse;
import com.roomx.shared.dto.user.response.UserResponse;
import com.roomx.shared.dto.user.response.UserRoleResponse;
import com.roomx.application.service.user.RoleAppService;
import com.roomx.application.service.user.UserAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserAppService userAppService;
    RoleAppService roleAppService;

    @PostMapping("/{userId}/roles/{roleName}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResultResponse<?> addUserRoles(@PathVariable String userId, @PathVariable String roleName) {
        var result = roleAppService.addRoleForUser(UUID.fromString(userId), roleName);
        return ResultResponse.<UserRoleResponse>builder()
                .result(result).build();
    }

    @DeleteMapping("/{userId}/roles/{roleName}")
    public ResultResponse<?> removeUserRoles(@PathVariable String userId, @PathVariable String roleName) {
        var result = roleAppService.removeRoleForUser(UUID.fromString(userId), roleName);
        return ResultResponse.<UserRoleResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping
    public ResultResponse<?> getListPageUser(
            @ModelAttribute UserQueryFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        var result = userAppService.getListUserPages(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<UserResponse>>builder()
                .result(result)
                .build();
    }


    @PostMapping
    public ResultResponse<?> createUser(@Validated @RequestBody UserCreateRequest request) {
        var result = userAppService.createUser(request);
        return ResultResponse.<UserCreateResponse>builder()
                .result(result)
                .build();
    }


    @GetMapping("/{userId}")
    public ResultResponse<?> getUserDetail(@PathVariable String userId) {
        var result = userAppService.getDetailUser(userId);
        return ResultResponse.<UserInfoReponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/info")
    public ResultResponse<?> getUserInfo() {
        var result = userAppService.getUserInfo();
        return ResultResponse.<UserInfoReponse>builder()
                .result(result)
                .build();
    }
















    @GetMapping("/auth-info")
    public Map<String, Object> getAuthInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaims();
        }

        Locale locale = LocaleContextHolder.getLocale();
        log.info("Current locale: {}", locale);
        System.out.println("user id current: " + authentication.getName());

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

    @GetMapping("/tenant/{data}")
    public ResultResponse<?> getTenantCurrent(@PathVariable String data) {
        log.info("getTenantCurrent: {}", userAppService.getUserDetail(data));

        return ResultResponse.<String>builder().result(userAppService.getTenant()).build();
    }




}
