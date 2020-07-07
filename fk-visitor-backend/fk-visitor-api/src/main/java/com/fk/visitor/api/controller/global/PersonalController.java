package com.fk.visitor.api.controller.global;


import cn.kinkii.novice.framework.controller.BaseModelController;
import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.GlobalMessage;
import cn.kinkii.novice.framework.controller.exception.InternalServiceException;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.entity.utils.NodeAssembler;
import cn.kinkii.novice.security.context.KAuthenticatingContext;
import com.fk.visitor.api.controller.global.req.PersonalBasicUpdateReq;
import com.fk.visitor.api.controller.global.req.PersonalPasswordUpdateReq;
import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.entity.OperatorRole;
import com.fk.visitor.api.entity.SysMenu;
import com.fk.visitor.api.repository.OperatorRepository;
import com.fk.visitor.api.repository.SysRoleRepository;
import com.fk.visitor.api.utils.OperatorUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/personal")
@Api(tags = {"个人中心"}, description = "/personal")
public class PersonalController extends BaseModelController<Operator, String> {

    private static NodeAssembler<SysMenu, String> SYS_MENU_ASSEMBLER = new NodeAssembler<>();

    @Autowired
    private KAuthenticatingContext kAuthenticatingContext;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @RequestMapping(value = "/menus", method = RequestMethod.GET)
    public Collection<SysMenu> myMenus(Principal principal) {
        if (principal == null) {
            return null;
        }
        Operator operator = OperatorUtils.parse(principal);
        Set<SysMenu> allMenus = new LinkedHashSet<>();
        if (operator.getRoles() != null) {
            for (OperatorRole role : operator.getRoles()) {
                sysRoleRepository.findById(role.getKeyCode()).ifPresent(sysRole -> allMenus.addAll(sysRole.getMenus()));
            }
        }
        return SYS_MENU_ASSEMBLER.assemble(allMenus);
    }

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Operator queryMyInfo(Principal principal) {
        if (principal == null) {
            return null;
        }
        return operatorRepository.findById(OperatorUtils.parse(principal).getId()).orElseThrow(() -> new InvalidParamException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey())));
    }

    @RequestMapping(value = "/update/info", method = RequestMethod.POST)
    public BaseResult updateMyInfo(PersonalBasicUpdateReq personalBasicUpdateReq, Principal principal) {
        if (principal == null) {
            return null;
        }
        Operator currentOperator = OperatorUtils.parse(principal);
        Operator newOperator = operatorRepository.findById(currentOperator.getId()).orElseThrow(() -> new InvalidParamException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey())));
        newOperator = personalBasicUpdateReq.toTarget(newOperator);
        try {
            operatorRepository.update(newOperator);
            // remove to force update cache
            kAuthenticatingContext.accountCache().removeUserFromCache(currentOperator.getId());
            return BaseResult.success(getMessage(GlobalMessage.UPDATE_SUCCESS.getMessageKey()));
        } catch (RuntimeException ignored) {
            throw new InternalServiceException(getMessage(GlobalMessage.UPDATE_FAILURE.getMessageKey()));
        }
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public BaseResult updateMyPassword(PersonalPasswordUpdateReq personalPasswordUpdateReq, Principal principal) {
        if (principal == null) {
            return null;
        }
        Operator currentOperator = OperatorUtils.parse(principal);
        Operator newOperator = operatorRepository.findById(currentOperator.getId()).orElseThrow(() -> new InvalidParamException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey())));
        if (!newOperator.getPassword().equals(kAuthenticatingContext.passwordEncoder().encode(personalPasswordUpdateReq.getOldPassword()))) {
            throw new InvalidParamException("原密码错误");
        }
        try {
            newOperator.setPassword(kAuthenticatingContext.passwordEncoder().encode(personalPasswordUpdateReq.getNewPassword()));
            operatorRepository.patch(newOperator);
            return BaseResult.success(getMessage(GlobalMessage.UPDATE_SUCCESS.getMessageKey()));
        } catch (RuntimeException ignored) {
            throw new InternalServiceException(getMessage(GlobalMessage.UPDATE_FAILURE.getMessageKey()));
        }
    }


    @RequestMapping(value = "/check/mobile")
    public Boolean checkMobile(String mobile, Principal principal) {
        if (principal == null) {
            return null;
        }
        return isCoherent(operatorRepository.findByMobile(mobile), OperatorUtils.parse(principal).getId());
    }

    @RequestMapping(value = "/check/email")
    public Boolean checkEmail(String email, Principal principal) {
        if (principal == null) {
            return null;
        }
        return isCoherent(operatorRepository.findByEmail(email), OperatorUtils.parse(principal).getId());
    }

}
