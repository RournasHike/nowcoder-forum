package com.nowcoder.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 配置Swagger2
 * @author Alex
 * @version 1.0
 * @date 2022/1/30 15:39
 */
@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * @Description 装配Swagger的Docket对象,配置Swagger基础信息
     * @param
     * @return  Docket对象
     * @throws
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.nowcoder.community"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * @Description 配置接口信息，返回Swagger接口对象
     * @param
     * @return  ApiInfo对象
     * @throws
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Spring Boot中使用Swagger2构建RESTful APIs")
                .description("牛客网社区论坛")
                .termsOfServiceUrl("http://www.nowcoder.com/community")
                .contact(new Contact("nowcoder","http://www.nowcoder.com/","32912299355@qq.com"))
                .version("1.0")
                .build();
    }
}
