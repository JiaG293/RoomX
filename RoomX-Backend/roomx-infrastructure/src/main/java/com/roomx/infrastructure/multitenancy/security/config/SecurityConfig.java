package com.roomx.infrastructure.multitenancy.security.config;

import com.roomx.infrastructure.multitenancy.keycloak.config.KeycloakClientRoleConverter;
import com.roomx.infrastructure.multitenancy.keycloak.config.KeycloakRealmRoleConverter;
import com.roomx.infrastructure.multitenancy.security.context.filter.TenantContextFilter;
import com.roomx.infrastructure.multitenancy.security.oauth.JwtAuthenticationEntryPoint;
import com.roomx.infrastructure.multitenancy.security.oauth.MultiTenantJwtDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.*;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private String[] public_endpoints = {
            "/api/v1/auth/login",
            "/api/v1/auth/introspect",
            "/api/v1/auth/logout",
            "/api/v1/auth/refresh",
            "/api/v1/users/register",
            "/api/v1/users/check",
            "/api/v1/test",
            "/api/v1/demo/public/**",
            "/test/**",
            "/actuator/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "/webjars/**",
//            "/test/**"

    };


    private final KeycloakRealmRoleConverter keycloakRealmRoleConverter;
    private final KeycloakClientRoleConverter keycloakClientRoleConverter;
    private final MultiTenantJwtDecoder multiTenantJwtDecoder;
    private final TenantContextFilter tenantContextFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // Endpoints
        httpSecurity
                .authorizeHttpRequests(request -> request.requestMatchers(HttpMethod.POST, public_endpoints)
                        .permitAll()
                        .requestMatchers(HttpMethod.GET, public_endpoints)
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                );


        // Resource server
        httpSecurity
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwtConfigurer -> jwtConfigurer
                                .decoder(multiTenantJwtDecoder)
                                .jwtAuthenticationConverter(jwtAuthenticationConverter()))
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint));//new JwtAuthenticationEntryPoint()


        // Thêm TenantContextFilter trước BearerTokenAuthenticationFilter để filter lấy ra tenantid xác định jwkissuer
        httpSecurity.addFilterBefore(tenantContextFilter, BearerTokenAuthenticationFilter.class);

        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.cors(cors -> cors.configurationSource(corsConfigurationSource()));

        return httpSecurity.build();
    }




    //Cors cấu hình với spring framework
/*
 @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration);

        return new CorsFilter(urlBasedCorsConfigurationSource);
    }
*/


    //Cors with spring security
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(
                "http://localhost:5173", // origin cho web app
                "http://localhost:5173",
                "exp://192.168.1.127:8088", // Origin của expo app
                "com.roomx.mobile://" // fetch api from axios))
        ));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList(
                "Authorization",
                "Content-Type",
                "X-Requested-With",
                "X-TenantId",
                "Accept-Language"
        ));
        configuration.setAllowCredentials(true); // Quan trọng để gửi cookies (JSESSIONID)
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


    // Dùng cho jwt custom
/*
@Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
*/


    // Dùng cho jwt keycloak
/*
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("roles"); // mapping authority với trường roles thay vì scope

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);

        return jwtAuthenticationConverter;
    }
*/

    private JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            Map<String, Object> claims = jwt.getClaims();

            // Nếu cần quản lý realm
            /*authorities.addAll(Objects.requireNonNull(keycloakRealmRoleConverter.convert(jwt)));
            authorities.addAll(Objects.requireNonNull(keycloakClientRoleConverter.convert(jwt)));*/

            // Roles Permissons của hệ thống


            // Xử lý roles
            Optional.ofNullable(claims.get("roles"))
                    .filter(List.class::isInstance)
                    .map(List.class::cast)
                    .ifPresent(roles -> roles.forEach(role ->
                            authorities.add(new SimpleGrantedAuthority(role.toString().toUpperCase()))));

            // Xử lý permissions
            Optional.ofNullable(claims.get("permissions"))
                    .filter(List.class::isInstance)
                    .map(List.class::cast)
                    .ifPresent(permissions -> permissions.forEach(permission ->
                            authorities.add(new SimpleGrantedAuthority("PERMISSION_" + permission.toString().toUpperCase()))));

            return authorities;
        });
        return converter;
    }


    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
