package com.fk.visitor.api.controller.system.operator;

import com.fk.visitor.lib.entity.system.Operator;
import com.fk.visitor.lib.repository.system.OperatorRepository;
import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.GlobalMessage;
import cn.kinkii.novice.framework.repository.ModelRepository;
import cn.kinkii.novice.security.context.KAuthenticatingContext;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.InvalidParameterException;
import java.security.Principal;

@RestController
@RequestMapping("/operator")
@Api(tags = {"账号"})
public class OperatorCRUDController extends BaseModelCRUDController<Operator, String> {

    @Autowired
    private OperatorRepository operatorRepository;
    @Autowired
    private KAuthenticatingContext kAuthenticatingContext;

    @Override
    protected ModelRepository<Operator, String> getRepository() {
        return operatorRepository;
    }

    @Override
    protected Operator handleCreate(Operator model, Principal principal, HttpServletRequest request) {
        model.setPassword(kAuthenticatingContext.passwordEncoder().encode(model.getPlainPassword()));
        return model;
    }

    @Override
    protected Operator handleUpdate(Operator model, Principal principal, HttpServletRequest request) {
        Operator operator = operatorRepository.findById(model.getId()).orElseThrow(() -> new InvalidParameterException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey())));
        if (StringUtils.isNotBlank(model.getPassword())) {
            operator.setPassword(kAuthenticatingContext.passwordEncoder().encode(model.getPassword()));
        }
        if (kAuthenticatingContext != null) {
            kAuthenticatingContext.accountCache().removeUserFromCache(operator.getUsername());
        }
        operator.setUsername(model.getUsername());
        operator.setName(model.getName());
        operator.setRoles(model.getRoles());
        operator.setMobile(model.getMobile());

        return operator;
    }

    @Override
    protected Operator handlePatch(Operator model, Principal principal, HttpServletRequest request) {
        Operator operator = operatorRepository.findById(model.getId()).orElseThrow(() -> new InvalidParameterException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey())));
        if (StringUtils.isNotBlank(model.getPassword())) {
            model.setPassword(kAuthenticatingContext.passwordEncoder().encode(model.getPassword()));
        }
        if (kAuthenticatingContext != null) {
            kAuthenticatingContext.accountCache().removeUserFromCache(operator.getUsername());
        }

        return model;
    }
}
