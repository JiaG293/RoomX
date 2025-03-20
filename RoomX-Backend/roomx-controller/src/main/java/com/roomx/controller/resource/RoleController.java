package com.roomx.controller.resource;

import com.roomx.shared.dto.user.request.RoleCreateRequest;
import com.roomx.shared.dto.user.response.RoleResponse;
import com.roomx.application.service.user.RoleAppService;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/api/v1/roles")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleAppService roleAppService;


    @GetMapping
    public ResultResponse<?> getPageRole() {
        return ResultResponse.<String>builder()
                .result("Get page role")
                .build();
    }

    @GetMapping("/all")
    public ResultResponse<?> getAllRole() {
        var result = roleAppService.findAllRole();
        return ResultResponse.<List<RoleResponse>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{userId}/not-assigned")
    public ResultResponse<?> getRolesNotAssignToUser(@PathVariable String userId) {
        var result = roleAppService.findRolesNotAssignedToUser(userId);
        return ResultResponse.<List<RoleResponse>>builder()
                .result(result)
                .build();
    }





    @PostMapping
    public ResultResponse<?> createRole(@RequestBody RoleCreateRequest roleCreateRequest) {
        return ResultResponse.<RoleResponse>builder()
                .result(roleAppService.createRole(roleCreateRequest))
                .build();
    }

    @DeleteMapping("/{roleName}")
    public ResultResponse<?> deleteRole(@PathVariable String roleName){
        return ResultResponse.<String>builder().result("deleteRole").build();
    }

    @PutMapping("/{roleName}")
    public ResultResponse<?> updateRole(){
        return ResultResponse.<String>builder().result("updateRole").build();
    }


    @PostMapping("/{roleName}/permissions/{permissionName}")
    public ResultResponse<?> removePermissionFromRole(@PathVariable String roleName, @PathVariable String permissionName) {
        return ResultResponse.<String>builder()
                .result("remove permission from role")
                .build();
    }




}
