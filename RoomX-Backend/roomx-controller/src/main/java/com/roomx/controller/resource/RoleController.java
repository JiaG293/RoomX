package com.roomx.controller.resource;

import com.roomx.application.dto.user.request.RoleCreateRequest;
import com.roomx.application.dto.user.response.RoleResponse;
import com.roomx.application.service.user.RoleAppService;
import com.roomx.shared.exception.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleAppService roleAppService;


    @PostMapping
    public ResultResponse<?> createRole(@RequestBody RoleCreateRequest roleCreateRequest) {
        return ResultResponse.<RoleResponse>builder()
                .result(roleAppService.createRole(roleCreateRequest))
                .build();
    }

    @PostMapping("/{roleName}/permissions/{permissionName}")
    public ResultResponse<?> assignPermissionToRole(@PathVariable String roleName, @PathVariable String permissionName) {
        return ResultResponse.<RoleResponse>builder()
                .result(roleAppService.assignPermission(roleName, permissionName))
                .build();
    }


}
