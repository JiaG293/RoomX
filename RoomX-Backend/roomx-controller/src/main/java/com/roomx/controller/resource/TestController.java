package com.roomx.controller.resource;


import com.roomx.application.service.TestAppService;
import com.roomx.shared.exception.api.ResultResponse;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/testing")
@OpenAPIDefinition(info = @Info(title = "TEST API", version = "v1", description = "API TEST"))
public class TestController {
    TestAppService testAppService;


    @GetMapping
    public ResultResponse<?> test() {

        var result = testAppService.testAppService();
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }

    @PostMapping("/minio")
    public ResultResponse<?> test(
            @RequestParam("files") List<MultipartFile> request,
            @RequestParam("makePrivate") boolean makePrivate,
            @RequestParam("path") String path
    ) {

        var result = testAppService.testMinio(request, makePrivate, path);
        return ResultResponse.<Object>builder()
                .result(result)
                .build();
    }
}
