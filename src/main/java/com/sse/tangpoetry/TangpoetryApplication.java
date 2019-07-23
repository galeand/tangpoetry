package com.sse.tangpoetry;

//import com.alibaba.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@EnableDubbo

//@EnableDiscoveryClient //启用springcloud服务注册和发现，标志着这个工程需要引入springcloud支持，在build.gradle中配置
@MapperScan("com.sse.tangpoetry.mapper")
@SpringBootApplication
public class TangpoetryApplication {

    public static void main(String[] args) {
        SpringApplication.run(TangpoetryApplication.class, args);
    }

}
