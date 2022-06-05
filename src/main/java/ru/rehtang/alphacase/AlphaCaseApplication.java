package ru.rehtang.alphacase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AlphaCaseApplication {
    public static void main(String[] args) {
        SpringApplication.run(AlphaCaseApplication.class, args);
    }
}
