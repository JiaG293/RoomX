package com.roomx.controller.resource;

import com.roomx.application.dto.request.LoginRequest;
import com.roomx.application.dto.request.TokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/test/auth")
public class AuthController {

    @Value("${keycloak.auth-server-url}")
    private String authServerUrl;

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.resource}")
    private String clientId;

    @Value("${keycloak.credentials.secret}")
    private String clientSecret;

    private final RestTemplate restTemplate = new RestTemplate();

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
            map.add("client_id", clientId);
            map.add("client_secret", clientSecret);
            map.add("grant_type", "password");
            map.add("username", request.getUsername());
            map.add("password", request.getPassword());

            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

            String url = authServerUrl + "/realms/" + realm + "/protocol/openid-connect/token";

            ResponseEntity<TokenResponse> response = restTemplate.postForEntity(
                    url,
                    entity,
                    TokenResponse.class
            );

            return ResponseEntity.ok(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(401).body("Login failed");
        }
    }
}
