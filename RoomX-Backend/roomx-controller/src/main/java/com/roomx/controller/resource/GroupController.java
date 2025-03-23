package com.roomx.controller.resource;

import com.roomx.application.service.user.GroupAppService;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.response.GroupResponse;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class GroupController {
    GroupAppService groupAppService;

    @PostMapping
    public ResultResponse<?> createGroupForAdmin(@Validated @RequestBody GroupCreateAdminRequest request) {
        var result = groupAppService.createGroupForAdmin(request);
        return ResultResponse.<GroupResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{groupId}/members/{memberId}")
    public ResultResponse<?> addMemberGroupForAdmin(
            @PathVariable String groupId,
            @PathVariable String memberId) {
        var result = groupAppService.addMemberGroupForAdmin(groupId, memberId);
        return ResultResponse.<GroupResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{groupId}/members")
    public ResultResponse<?> addMemberGroupForAdmin(
            @PathVariable String groupId,
            @Validated @RequestBody List<String> memberList) {
        var result = groupAppService.addMemberListGroupForAdmin(groupId, memberList);
        return ResultResponse.<GroupResponse>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{groupId}/members/{memberId}/exists")
    public ResultResponse<?> checkMemberIsExistedGroup(
            @PathVariable String groupId,
            @PathVariable String memberId) {
        var result = groupAppService.checkMemberIsExistedGroup(groupId, memberId);
        return ResultResponse.<Boolean>builder()
                .result(result)
                .build();
    }
}
