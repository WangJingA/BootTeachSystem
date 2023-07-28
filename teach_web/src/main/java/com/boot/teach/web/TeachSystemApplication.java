package com.boot.teach.web;



import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * 启动类
 */

@EnableSwagger2
@MapperScan(basePackages = {"com.boot.teach.dao.**"})
@ComponentScan(basePackages = {"com.boot.teach.**"})
@SpringBootApplication
public class TeachSystemApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeachSystemApplication.class,args);
    }
}
