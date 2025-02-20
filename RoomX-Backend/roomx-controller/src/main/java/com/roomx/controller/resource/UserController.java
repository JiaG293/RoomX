package com.roomx.controller.resource;

import com.roomx.application.dto.request.UserQueryFilterRequest;
import com.roomx.application.dto.request.UserCreateRequest;
import com.roomx.application.dto.response.UserCreateResponse;
import com.roomx.application.dto.response.UserPageResponse;
import com.roomx.application.dto.response.UserResponse;
import com.roomx.application.service.employee.UserApplicationService;
import com.roomx.shared.exception.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    UserApplicationService userApplicationService;



    @PostMapping
    public ResultResponse<?> createUser(@RequestBody UserCreateRequest request) {
        return ResultResponse.<UserCreateResponse>builder().result(userApplicationService.createUser(request)).build();
    }


   /* @GetMapping("/info")
    public ResultResponse<?> getCurrentUser() {
        return ResultResponse.<UserApp>builder().result(userApplicationService.getUser()).build();
    }*/
  /*  @PreAuthorize("HAS_ROLE_SUPER_ADMIN")
    @PostMapping
    public ResultResponse<?> createUser(@RequestBody UserCreationRequest request) {
        log.info("request:  {}", request);
        return ResultResponse.<UserResponse>builder().result(keycloakUserService.createUser(request)).build();
    }*/

    @GetMapping("/taolao")
    public Map<String, Object> getAuthInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            return jwt.getClaims();
        }

        Locale locale = LocaleContextHolder.getLocale();
        log.info("Current locale: {}", locale);

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
        log.info("getTenantCurrent: {}", userApplicationService.getUserDetail(data));

        return ResultResponse.<String>builder().result(userApplicationService.getTenant()).build();
    }

    @GetMapping
    public ResultResponse<?> getListPageUser(
            @ModelAttribute UserQueryFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
            ) {


        return ResultResponse.<UserPageResponse>builder()
                .result(userApplicationService.getListUserPages(filter, page, size, sortBy, direction))
                .build();
    }


}
