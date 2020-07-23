package com.fk.visitor.lib;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "project")
public class ProjectConfig {

    private String name;
    private Boolean isDebug = false;

}
