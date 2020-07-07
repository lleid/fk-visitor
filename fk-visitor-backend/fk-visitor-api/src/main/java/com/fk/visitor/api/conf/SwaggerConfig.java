package com.fk.visitor.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final String API_TITLE = "iaas-mercury";
    private static final String API_DESCRIPTION = "管理服务接口定义";
    private static final String API_VERSION = "1.0.0-SNAPSHOT";

    private static final String API_PACKAGES = "com.fk.visitor";

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title(API_TITLE).description(API_DESCRIPTION).version(API_VERSION).build())
                .select()
                .apis(RequestHandlerSelectors.basePackage(API_PACKAGES))
                .paths(PathSelectors.any())
                .build();
    }
}
