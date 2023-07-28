package com.boot.teach.common.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger config 配置类
 */
@Configuration
public class SwaggerConfig implements WebMvcConfigurer {
    @Bean
    public ApiInfo apiInfo(){
        return new ApiInfoBuilder().title("高校教学系统teach-system接口文档").description("接口文档")
                .contact(new Contact("WangJing","","1670373895@qq.com"))
                .version("V1.0")
                .build();
    }

    @Bean
    public Docket baseApiInfo(){
        return  new Docket(DocumentationType.OAS_30).apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.boot.teach.web.controllers"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Swagger 非全局Head参数（Token）配置
     */
    private List<SecurityScheme> securitySchemes() {
        List<SecurityScheme> securitySchemes = new ArrayList<>();
        securitySchemes.add(new ApiKey("Authorization", "token", "header"));
        return securitySchemes;
    }

    /**
     * 授权信息全局应用
     */
    private List<SecurityContext> securityContexts() {
        List<SecurityContext> securityContexts = new ArrayList<>();
        securityContexts.add(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()).build());
        return securityContexts;
    }

    /**
     * Swagger 全局、无需重复输入的Head参数（Token）配置
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        List<SecurityReference> securityReferences = new ArrayList<>();
        securityReferences.add(new SecurityReference("token", authorizationScopes));
        return securityReferences;
    }

}
