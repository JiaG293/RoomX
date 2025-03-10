/*
package com.roomx.shared.exception.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
public class LocaleConfig { // Tạo 1 class config

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(new Locale("en"), new Locale("vi", "VN"))); // Hỗ trợ tiếng Anh và tiếng Việt
        resolver.setDefaultLocale(new Locale("vi", "VN")); // Locale mặc định là tiếng Việt
        return resolver;
    }
}*/
