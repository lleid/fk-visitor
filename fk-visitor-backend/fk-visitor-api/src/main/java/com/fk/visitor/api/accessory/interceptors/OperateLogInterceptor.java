package com.fk.visitor.api.accessory.interceptors;

import com.fk.visitor.lib.entity.system.OperateLog;
import com.fk.visitor.lib.repository.system.OperateLogRepository;
import cn.kinkii.novice.framework.controller.utils.RequestUtils;
import cn.kinkii.novice.framework.utils.KJsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Slf4j
public class OperateLogInterceptor implements HandlerInterceptor {

    @Autowired
    private OperateLogRepository operateLogRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (request.getUserPrincipal() != null) {
            try {
                OperateLog operateLog = new OperateLog(request.getUserPrincipal().getName(), new Date(), request.getRequestURI(), request.getMethod(), KJsonUtils.toJson(request.getParameterMap()), RequestUtils.getIpAddress());
                log.info(KJsonUtils.toJson(operateLog));
                operateLogRepository.create(operateLog);
            } catch (Exception ignored) {
            }
        }
    }
}
