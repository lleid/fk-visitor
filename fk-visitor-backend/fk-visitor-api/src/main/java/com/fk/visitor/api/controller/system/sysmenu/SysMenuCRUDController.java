package com.fk.visitor.api.controller.system.sysmenu;


import com.fk.visitor.api.entity.SysMenu;
import com.fk.visitor.api.repository.SysMenuRepository;
import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.GlobalMessage;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.repository.ModelRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/sys/menu")
@Api(tags = {"菜单管理"}, description = "/sys/menu")
public class SysMenuCRUDController extends BaseModelCRUDController<SysMenu, Long> {

    @Autowired
    private SysMenuRepository sysMenuRepository;

    @Override
    protected ModelRepository<SysMenu, Long> getRepository() {
        return sysMenuRepository;
    }

    @Override
    protected SysMenu handleCreate(SysMenu model, Principal principal, HttpServletRequest request) {
        if (sysMenuRepository.findByCode(model.getCode()) != null) {
            throw new InvalidParamException("菜单序号已存在");

        }
        if (sysMenuRepository.findByAlias(model.getAlias()) != null) {
            throw new InvalidParamException("菜单别名已存在");
        }

        model.setRelatedUrls(model.getRelatedUrlList());

        return model;
    }

    @Override
    protected SysMenu handleUpdate(SysMenu model, Principal principal, HttpServletRequest request) {
        if (!isCoherent(sysMenuRepository.findByCode(model.getCode()), model.getId())) {
            throw new InvalidParamException("角色名已经存在");
        }
        if (!isCoherent(sysMenuRepository.findByAlias(model.getAlias()), model.getId())) {
            throw new InvalidParamException("角色别名已经存在");
        }
        SysMenu currentMenu = sysMenuRepository.findById(model.getId()).orElseThrow(() -> new InvalidParamException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey())));
        sysMenuRepository.updateParentCode(currentMenu.getCode(), model.getCode());

        model.setRelatedUrls(model.getRelatedUrlList());

        return model;
    }
}
