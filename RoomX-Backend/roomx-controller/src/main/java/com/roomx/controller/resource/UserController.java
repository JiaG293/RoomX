package com.roomx.controller.resource;

import com.roomx.shared.dto.user.request.*;
import com.roomx.shared.dto.user.response.*;
import com.roomx.application.service.user.RoleAppService;
import com.roomx.application.service.user.UserAppService;
import com.roomx.shared.exception.api.ResultResponse;
import jakarta.validation.constraints.Email;
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
import org.springframework.security.core.userdetails.UserDetails;
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

   /* @GetMapping
    public ResultResponse<?> getListPageUser(
            @ModelAttribute UserQueryFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "updatedAt") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        var result = userAppService.getFilterSearchPage(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<UserResponse>>builder()
                .result(result)
                .build();
    }*/

    @GetMapping("/filters")
    public ResultResponse<?> getListPageUser(
            @ModelAttribute UserFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "lastName") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {

        var result = userAppService.getFilterSearchPage(filter, page, size, sortBy, direction);

        return ResultResponse.<Page<UserPageResponse>>builder()
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

    @PatchMapping
    public ResultResponse<?> updateInfoUser(@Validated @RequestBody UserUpdateInfoRequest request) {
        var result = userAppService.updateInfoUser(request);
        return ResultResponse.<UserResponse>builder()
                .result(result)
                .build();
    }

    @PatchMapping("/{userId}")
    public ResultResponse<?> updateUser(@PathVariable String userId, @Validated @RequestBody UserUpdateRequest request) {
        var result = userAppService.updateUser(userId,request);
        return ResultResponse.<UserResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/check-email/{email}")
    public ResultResponse<?> checkEmail(@PathVariable @Validated @Email(message = "valid.email.invalid") String email) {
        var result = userAppService.checkEmailExist(email);
        return ResultResponse.<Boolean>builder()
                .result(result)
                .build();
    }


    @DeleteMapping("/{userId}")
    public ResultResponse<?> deleteUser(@PathVariable String userId) {
        userAppService.softDeleteUser(userId);
        return ResultResponse.<Void>builder()
                .build();
    }

    @DeleteMapping("/delete/{userId}")
    public ResultResponse<?> hardDeleteUser(@PathVariable String userId) {
        userAppService.hardDeleteUser(userId);
        return ResultResponse.<Void>builder()
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






}
