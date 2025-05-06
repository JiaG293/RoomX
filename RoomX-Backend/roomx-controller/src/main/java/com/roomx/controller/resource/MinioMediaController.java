package com.roomx.controller.resource;

import com.roomx.application.service.file.MinioAppService;
import com.roomx.shared.exception.api.ResultResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/s3")
@OpenAPIDefinition(info = @Info(title = "Upload media files with s3 minio", version = "v1", description = "Minio s3"))
public class MinioMediaController {

    MinioAppService minioAppService;


    @PostMapping("/users/avatar")
    public ResultResponse<?> uploadAvatar(
            @RequestParam("file") MultipartFile file
    ) {
        var result = minioAppService.uploadFile(file, false, "profile/avatar", null);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @PostMapping("/rooms/{roomId}")
    public ResultResponse<?> uploadRoomImage(
            @PathVariable String roomId,
            @RequestParam("files") List<MultipartFile> request
    ) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("roomId", roomId);
        var result = minioAppService.uploadFiles(request, false, "rooms", metadata);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @PostMapping("/equipments/{equipmentId}")
    public ResultResponse<?> uploadEquipmentImage(
            @PathVariable String equipmentId,
            @RequestParam("files") List<MultipartFile> request
    ) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("equipmentId", equipmentId);
        var result = minioAppService.uploadFiles(request, false, "equipments", metadata);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }


    @PostMapping("/services/{serviceId}")
    public ResultResponse<?> uploadServiceImage(
            @PathVariable String serviceId,
            @RequestParam("files") List<MultipartFile> request
    ) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("serviceId", serviceId);
        var result = minioAppService.uploadFiles(request, false, "services", metadata);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }
}
