package com.fk.visitor.api;

import cn.kinkii.novice.framework.repository.BaseModelRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@ComponentScan(basePackages = {"com.fk.visitor","cn.kinkii.novice.security","cn.kinkii.novice.accessory","cn.kinkii.novice.framework"})
@EntityScan(basePackages = {"com.fk.visitor.lib.entity", "com.fk.visitor.api.entity"})
@EnableJpaRepositories(basePackages = {"com.fk.visitor.lib.repository", "com.fk.visitor.api.repository"}, repositoryBaseClass = BaseModelRepository.class)
@Configuration
@EnableSwagger2
@EnableScheduling
@EnableAsync
public class ApiStarter {
    public static void main(String... args) {
        SpringApplication.run(ApiStarter.class, args);
    }
}
