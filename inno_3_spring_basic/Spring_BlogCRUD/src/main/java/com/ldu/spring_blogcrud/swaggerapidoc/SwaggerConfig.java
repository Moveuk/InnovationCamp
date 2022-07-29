package com.ldu.spring_blogcrud.swaggerapidoc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig extends WebMvcConfigurationSupport {
    //스웨거 REST API DOC 페이지에 안내될 설명 설정.
    private static final String API_NAME = "이노베이션 Spring 숙련 주차 과제 프로젝트";
    private static final String API_VERSION = "0.0.1";
    private static final String API_DESCRIPTION = "과제 API 명세";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(API_NAME)
                .version(API_VERSION)
                .description(API_DESCRIPTION)
                .build();
    }

    @Bean
    public Docket commonApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("swg-post")//빈설정에 대한 그루핑을 한 그룹에 대한 구분자 값 설정.
                .apiInfo(this.apiInfo())//스웨거 설명
                .select()//apis, paths를 사용하주기 위한 builder
                .apis(RequestHandlerSelectors.basePackage("com.ldu.spring_blogcrud.controller"))//탐색할 클래스 필터링
                .paths(PathSelectors.any())//스웨거에서 보여줄 api 필터링
                .build();
    }

    @Override //swagger-ui.html 찾을 수 없다는 오류 발생시 추가.
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
