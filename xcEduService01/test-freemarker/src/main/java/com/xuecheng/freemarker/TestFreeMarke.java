package com.xuecheng.freemarker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * 启动类
 */
@SpringBootApplication
public class TestFreeMarke {

    public static void main(String[] args) {
        SpringApplication.run(TestFreeMarke.class,args);
    }

    @Bean
    public RestTemplate restTemplate1(){
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory());
    }
}
