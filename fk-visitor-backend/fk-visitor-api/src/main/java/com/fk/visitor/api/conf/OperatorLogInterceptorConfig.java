package com.fk.visitor.api.conf;

import com.fk.visitor.api.accessory.interceptors.OperateLogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class OperatorLogInterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new OperateLogInterceptor()).addPathPatterns("/**");
    }

}
