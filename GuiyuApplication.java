package com.geek.guiyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.geek.guiyu.infrastructure.dao")
public class GuiyuApplication {

    public static void main(String[] args) {
        SpringApplication.run(GuiyuApplication.class, args);
    }

}
