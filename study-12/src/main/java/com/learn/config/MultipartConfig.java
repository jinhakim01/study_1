package com.learn.config;


import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

import jakarta.servlet.MultipartConfigElement;

@Configuration
public class MultipartConfig {

    // Multipart Resolver 설정
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }

    // 파일 업로드 용량 및 경로 설정
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(20L));// 최대 파일 크기 "20MB"
        factory.setMaxRequestSize(DataSize.ofMegabytes(30L));// 전체 요청(파일 여러 개) 크기 제한 "30MB"
        factory.setLocation("uploads"); // 프로젝트 루트 내 uploads 디렉터리 사용 (자동생성)
        return factory.createMultipartConfig();
    }
}