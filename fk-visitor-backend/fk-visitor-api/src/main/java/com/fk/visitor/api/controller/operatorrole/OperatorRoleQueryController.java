package com.fk.visitor.api.controller.operatorrole;

import com.fk.visitor.api.controller.operatorrole.query.OperatorRoleQuery;
import com.fk.visitor.api.entity.OperatorRole;
import com.fk.visitor.api.repository.OperatorRoleRepository;
import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operator/role")
@Api(tags = {"用户角色管理"}, description = "/operator/role")
public class OperatorRoleQueryController extends BaseJpaQueryController<OperatorRole, Long, OperatorRoleQuery> {

    @Autowired
    private OperatorRoleRepository operatorRoleRepository;

    @Override
    protected ModelRepository<OperatorRole, Long> getRepository() {
        return operatorRoleRepository;
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(Long id, String name) {
        return isCoherent(operatorRoleRepository.findByName(name), id);
    }

    @RequestMapping(value = "/check/key-code")
    public Boolean checkKeyCode(Long id, String keyCode) {
        return isCoherent(operatorRoleRepository.findByKeyCode(keyCode), id);
    }
}
