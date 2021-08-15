package com.fk.visitor.api.controller.system.sysrole;

import com.fk.visitor.api.entity.SysRole;
import com.fk.visitor.api.repository.SysRoleRepository;
import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/role")
@Api(tags = {"系统角色管理"}, description = "/sys/role")
public class SysRoleCRUDController extends BaseModelCRUDController<SysRole, String> {

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Override
    protected ModelRepository<SysRole, String> getRepository() {
        return sysRoleRepository;
    }

}
