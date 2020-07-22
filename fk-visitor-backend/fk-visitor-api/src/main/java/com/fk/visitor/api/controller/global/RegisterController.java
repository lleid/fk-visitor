package com.fk.visitor.api.controller.global;

import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.security.context.KAuthenticatingContext;
import com.fk.visitor.api.controller.global.req.RegisterReq;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.entity.OperatorRole;
import com.fk.visitor.lib.repository.OperatorRepository;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@Api(tags = {"注册"}, description = "注册")
@SuppressWarnings("all")
public class RegisterController {

    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private KAuthenticatingContext kAuthenticatingContext;

    @RequestMapping(value = "/pub/register", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult register(RegisterReq req, HttpServletRequest request) {
        if (StringUtils.isBlank(req.getUsername())) {
            throw new InvalidParamException("用户名不能为空");
        }
        if (StringUtils.isBlank(req.getMobile())) {
            throw new InvalidParamException("用户名不能为空");
        }

        if (operatorRepository.findByUsername(req.getUsername()) != null) {
            throw new InvalidParamException("用户名已经存在");
        }
        if (operatorRepository.findByMobile(req.getMobile()) != null) {
            throw new InvalidParamException("手机号已经存在");
        }
        Operator model = req.newTarget();
        model.setPassword(kAuthenticatingContext.passwordEncoder().encode(model.getPlainPassword()));
        model.setRoles(Lists.newArrayList(new OperatorRole() {{
            setId(2L);
        }}));

        operatorRepository.create(model);
        return BaseResult.success("操作成功");
    }
}
