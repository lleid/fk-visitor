package com.fk.visitor.api.controller.operatorrole;


import com.fk.visitor.api.controller.operatorrole.req.OperatorRoleReq;
import com.fk.visitor.api.entity.OperatorRole;
import com.fk.visitor.api.entity.SysMenu;
import com.fk.visitor.api.entity.SysPermission;
import com.fk.visitor.api.entity.SysRole;
import com.fk.visitor.api.repository.OperatorRepository;
import com.fk.visitor.api.repository.OperatorRoleRepository;
import com.fk.visitor.api.repository.SysPermissionRepository;
import com.fk.visitor.api.repository.SysRoleRepository;
import cn.kinkii.novice.framework.controller.GenericModelRequestCRUDController;
import cn.kinkii.novice.framework.controller.exception.InternalServiceException;
import cn.kinkii.novice.framework.repository.ModelRepository;
import cn.kinkii.novice.framework.utils.KJsonUtils;
import io.swagger.annotations.Api;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.stream.Collectors;

@Log
@RestController
@RequestMapping("/operator/role")
@Api(tags = {"管理用户角色"}, description = "/operator/role")
public class OperatorRoleCRUDController extends GenericModelRequestCRUDController<OperatorRole, Long, OperatorRoleReq> {

    @Autowired
    private OperatorRoleRepository operatorRoleRepository;

    @Autowired
    private SysRoleRepository sysRoleRepository;

    @Autowired
    private SysPermissionRepository sysPermissionRepository;

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    protected ModelRepository<OperatorRole, Long> getRepository() {
        return operatorRoleRepository;
    }

    private void createSysRole(OperatorRoleReq model) {
        SysRole sysRole = new SysRole();
        sysRole.setId(model.getKeyCode());
        if (model.getMenuIds()!=null) {
            sysRole.setMenus(model.getMenuIds().stream().map(SysMenu::new).collect(Collectors.toSet()));
        }
        if (model.getPermissionUrls()!=null) {
            sysRole.setPermissions(model.getPermissionUrls().stream()
                    .map(url -> sysPermissionRepository.existsByUrl(url) ? sysPermissionRepository.findFirstByUrlEquals(url) : sysPermissionRepository.save(new SysPermission(url)))
                    .collect(Collectors.toSet()));
        }
        sysRoleRepository.create(sysRole);
    }

    private void deleteSysRoleByRoleId(Long id) {
        operatorRoleRepository.findById(id).ifPresent(operatorRole -> sysRoleRepository.deleteById(operatorRole.getKeyCode()));
    }

    @Override
    protected OperatorRoleReq handleCreate(OperatorRoleReq model, Principal principal, HttpServletRequest request) {
        log.info(KJsonUtils.toJson(model));
        createSysRole(model);
        return model;
    }

    @Override
    protected OperatorRoleReq handleUpdate(Long id, OperatorRoleReq modelRequest, Principal principal, HttpServletRequest request) {
        deleteSysRoleByRoleId(id);
        createSysRole(modelRequest);
        return super.handleUpdate(id, modelRequest, principal, request);
    }

    @Override
    protected void handleDelete(Long id, Principal principal, HttpServletRequest request) {
        if (operatorRepository.existsByRolesContains(operatorRoleRepository.findById(id).orElseThrow(() -> new InternalServiceException("id异常")))) {
            throw new InternalServiceException("当前角色存在已关联用户, 无法删除!");
        }
        deleteSysRoleByRoleId(id);
    }

    //
//    @Override
//    protected OperatorRole handleUpdate(OperatorRole model, Principal principal, HttpServletRequest request) {
//        if (!isCoherent(operatorRoleRepository.findByName(model.getName()), model.getId())) {
//            throw new InvalidParamException("角色名已经存在");
//        }
//        if (!isCoherent(operatorRoleRepository.findByKeyCode(model.getKeyCode()), model.getId())) {
//            throw new InvalidParamException("角色别名已经存在");
//        }
//
//        sysRoleRepository.deleteById(model.getKeyCode());
//        createSysRole(model, request);
//        return model;
//    }
//
//    @Override
//    protected void handleDelete(Long aLong, Principal principal, HttpServletRequest request) {
//        OperatorRole operatorRole = getRepository().findById(aLong).orElseThrow(() -> new InvalidParamException(getMessage(GlobalMessage.DELETE_FAILURE_NOT_EXISTED.getMessageKey())));
//        sysRoleRepository.deleteById(operatorRole.getKeyCode());
//    }

}
