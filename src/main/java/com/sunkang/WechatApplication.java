package com.sunkang;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * Created by changxiang on 2017-04-01.
 * springboot启动类
 */
@SpringBootApplication//springboot应用
@EnableAutoConfiguration//打开自动配置
public class WechatApplication {
    public static void main(String[] args) {
//        SpringApplication springApplication= (SpringApplication) SpringApplication.run(WechatApplication.class,args);
        SpringApplication springApplication=new SpringApplication(WechatApplication.class);
        springApplication.run(args);
//        springApplication.setBanner(new Banner() {
//            @Override
//            public void printBanner(Environment environment, Class<?> aClass, PrintStream printStream) {
//
//            }
//        });
    }
}
