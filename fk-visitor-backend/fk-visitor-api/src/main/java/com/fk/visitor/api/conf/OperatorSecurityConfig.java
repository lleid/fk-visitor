package com.fk.visitor.api.conf;

import com.fk.visitor.lib.entity.OperateLog;
import com.fk.visitor.lib.repository.OperateLogRepository;
import com.fk.visitor.lib.repository.OperatorRepository;
import com.fk.visitor.api.service.OperatorAccount;
import cn.kinkii.novice.framework.controller.utils.RequestUtils;
import cn.kinkii.novice.framework.utils.KJsonUtils;
import cn.kinkii.novice.security.config.KSecurityConfigurerAdapter;
import cn.kinkii.novice.security.web.auth.KAuthSuccessAdditionalHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
@EnableWebSecurity
public class OperatorSecurityConfig extends KSecurityConfigurerAdapter {

    @Autowired
    private OperateLogRepository operateLogRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    protected void additionalConfigure(AuthenticationManagerBuilder auth) {
    }

    @Override
    protected void additionalConfigure(HttpSecurity http) {
    }

    @Bean("accountSuccessHandlers")
    public List<KAuthSuccessAdditionalHandler> buildAccountAuthSuccessHandlers() {
        List<KAuthSuccessAdditionalHandler> handlers = new ArrayList<>();
        handlers.add((request, response, authentication) -> {
            OperatorAccount account = (OperatorAccount) authentication.getPrincipal();
            if (account != null) {
                try {
                    operateLogRepository.create(new OperateLog(account.getName(), new Date(), request.getRequestURI(), RequestUtils.getIpAddress(), KJsonUtils.toJson(request.getParameterMap()), request.getMethod()));
                } catch (Exception ignored) {
                }
            }
        });
        handlers.add((request, response, authentication) -> {
            OperatorAccount account = (OperatorAccount) authentication.getPrincipal();
            if (account != null) {
                try {
                    operatorRepository.updateLastLoginAt(account.getId(), new Date());
                } catch (Exception ignored) {
                }
            }
        });
        return handlers;
    }
}
