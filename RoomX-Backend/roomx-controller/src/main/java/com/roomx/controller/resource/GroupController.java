package com.roomx.controller.resource;

import com.roomx.application.service.user.GroupAppService;
import com.roomx.shared.dto.booking.request.BookingListRequest;
import com.roomx.shared.dto.resource.request.GroupFilterRequest;
import com.roomx.shared.dto.resource.response.GroupFilterResponse;
import com.roomx.shared.dto.user.request.GroupCreateAdminRequest;
import com.roomx.shared.dto.user.response.GroupDetailResponse;
import com.roomx.shared.dto.user.response.GroupResponse;
import com.roomx.shared.exception.api.ResultResponse;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @DeleteMapping("/{groupId}/members/{memberId}")
    public ResultResponse<?> deleteMemberFromGroup(
            @PathVariable String groupId,
            @PathVariable String memberId) {
        groupAppService.deleteMemberFromGroup(groupId, memberId);
        return ResultResponse.<Void>builder()
                .build();
    }

    @DeleteMapping("/{groupId}/members")
    public ResultResponse<?> deleteMemberFromGroup(
            @PathVariable String groupId,
            @Validated @RequestBody List<String> members) {
        var result = groupAppService.deleteListMemberFromGroup(groupId, members);
        return ResultResponse.<Map<String, List<String>>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/filters")
    public ResultResponse<?> filterSearchGroup(
            @ModelAttribute GroupFilterRequest request,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "-1") Integer size,
            @RequestParam(defaultValue = "groupType") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        var result = groupAppService.filterSearchGroup(request, page, size, sortBy, direction);
        return ResultResponse.<Page<GroupFilterResponse>>builder()
                .result(result)
                .build();
    }


    @GetMapping("/{groupId}")
    public ResultResponse<?> getDeatailGroup(
            @PathVariable String groupId) {
        var result = groupAppService.getDetailGroup(groupId);
        return ResultResponse.<GroupDetailResponse>builder()
                .result(result)
                .build();
    }
}
