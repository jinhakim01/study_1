package com.learn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        // 모든 도메인에서의 요청을 허용 (개발용)
        config.setAllowedOrigins(List.of("http://localhost:3000"));

        // 특정 HTTP 메서드 허용
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));

        // 헤더 허용
        config.setAllowedHeaders(List.of("*"));

        // 인증 정보 허용
        config.setAllowCredentials(true);

        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}