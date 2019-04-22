package com.sse.tangpoetry;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.sse.tangpoetry.mapper")
@SpringBootApplication
public class TangpoetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TangpoetryApplication.class, args);
    }

}
