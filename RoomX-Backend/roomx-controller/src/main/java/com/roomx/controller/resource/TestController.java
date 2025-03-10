package com.roomx.controller.resource;


import com.roomx.application.service.event.impl.EventAppServiceImpl;
import com.roomx.shared.exception.api.ResultResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@OpenAPIDefinition(info = @Info(title = "TEST API", version = "v1", description = "API TEST"))
public class TestController {
    EventAppServiceImpl eventAppService;
    @NonFinal
    RestTemplate restTemplate = new RestTemplate();


    public String fallbackRateLimiter(Throwable throwable) {
        return "To manny request: " + throwable.getMessage();
    }

    @GetMapping("/1")
    @RateLimiter(name = "test1", fallbackMethod = "fallbackRateLimiter")
    public String testRateLimiter1() {
        return eventAppService.testApplication("hello application 1");
    }


    @GetMapping("/2")
    @RateLimiter(name = "test2", fallbackMethod = "fallbackRateLimiter")
    public String testRateLimiter2() {
        return eventAppService.testApplication("hello application 2");
    }


    public String fallbackCircuitBreaker(Throwable throwable) {
        return "Fail to get: " + throwable.getMessage();
    }

    @GetMapping("/3")
    @CircuitBreaker(name = "test3", fallbackMethod = "fallbackCircuitBreaker")
    public String testRateLimiter3() {
        int id = new Random().nextInt(10) + 1;
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/" + id, String.class);
    }

    @GetMapping("/4")
//    @RateLimiter(name = "4", fallbackMethod = "fallbackCircuitBreaker")
    public String testRateLimiter4() {
        int id = new Random().nextInt(10) + 1;
        return restTemplate.getForObject("https://jsonplaceholder.typicode.com/posts/" + id, String.class);
    }

    @GetMapping("/test")
    public ResultResponse<?> test() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        var targetId = "ea4e9c4c-a317-4064-8a5b-da2b338e4180";
        var result = "";
        System.out.println("authentication: " + authentication);
        return ResultResponse.<String>builder().result(result).build();
    }

  /*  @Operation(summary = "Lấy thông tin TEST", description = "Trả về thông tin TEST")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Thông tin TEST ID hợp lệ",
                    content = @Content(schema = @Schema(implementation = TestDTOResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Không tìm thấy TEST ID hợp lệ")
    })
    @GetMapping("/test")
    public ResultResponse<?> testRateLimiter5(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");


        return ResultResponse.<TestDTOResponse>builder().result(TestDTOResponse.builder().message(header + "\n1").build()).build();
    }*/

}