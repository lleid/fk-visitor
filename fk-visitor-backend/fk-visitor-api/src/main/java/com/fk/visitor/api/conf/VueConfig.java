package com.fk.visitor.api.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.config.PageableHandlerMethodArgumentResolverCustomizer;

/**
 * @author lizz
 */
@Configuration
public class VueConfig {

    @Bean
    public PageableHandlerMethodArgumentResolverCustomizer pageableCustomizer() {
        return resolver -> {
            resolver.setPageParameterName("pageNo");
            resolver.setSizeParameterName("pageSize");
            resolver.setOneIndexedParameters(true);
            resolver.setFallbackPageable(PageRequest.of(0, 20));
        };
    }
}
