package com.oversession;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * OverSession バックエンドアプリケーション
 * Spring Boot WebアプリケーションとしてHTTPサーバーを起動
 */
@SpringBootApplication
public class OverSessionApplication {
    
    public static void main(String[] args) {
        System.out.println("Starting OverSession Backend...");
        SpringApplication.run(OverSessionApplication.class, args);
        System.out.println("OverSession Backend started successfully!");
        System.out.println("Server running on http://localhost:8080");
        System.out.println("API endpoints available at /api/*");
    }
}