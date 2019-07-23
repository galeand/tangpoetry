package com.sse.tangpoetry.config;

import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
    }

    //知道怎么在配置类里面设置端口号吗，你还是对源码不熟
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
            @Override
            public void customize(ConfigurableWebServerFactory factory) {
                factory.setPort(9000);
            }
        };
    }

    //这里对比看一下几种lambda表达式的写法

    public void test01() {
        List<String> list = new ArrayList<>();
        list.add("hello");
        list.add("world");
        list.forEach(val -> {
            System.out.println(val);
        });
    }

//    @Bean
//    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> myWebServerFactory() {
//        return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>((ConfigurableWebServerFactory factory) -> {
//            factory.setPort(9000);
//        })
//    }
}
