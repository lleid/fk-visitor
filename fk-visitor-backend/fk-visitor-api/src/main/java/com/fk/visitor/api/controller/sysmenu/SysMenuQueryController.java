package com.fk.visitor.api.controller.sysmenu;


import cn.kinkii.novice.framework.controller.BaseModelController;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.entity.utils.NodeAssembler;
import com.fk.visitor.api.entity.SysMenu;
import com.fk.visitor.api.repository.SysMenuRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/sys/menu")
@Api(tags = {"菜单管理"}, description = "系统菜单查询")
public class SysMenuQueryController extends BaseModelController<SysMenu, Long> {

    private static NodeAssembler<SysMenu, String> SYS_MENU_ASSEMBLER = new NodeAssembler<>();

    @Autowired
    private SysMenuRepository sysMenuRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Collection<SysMenu> menus() {
        List<SysMenu> sysMenus = sysMenuRepository.findByOrderByCodeAsc();

        Set<SysMenu> allMenus = new LinkedHashSet<>(sysMenus);
        return SYS_MENU_ASSEMBLER.assemble(allMenus);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public SysMenu get(@PathVariable("id") Long id, Principal principal) {
        return sysMenuRepository.findById(id).orElseThrow(() -> new InvalidParamException("请求参数错误"));
    }

    @RequestMapping(value = "/check/code", method = RequestMethod.GET)
    public Boolean checkCode(Long id, String code) {
        return isCoherent(sysMenuRepository.findByCode(code), id);
    }

    @RequestMapping(value = "/check/alias", method = RequestMethod.GET)
    public Boolean checkAlias(Long id, String alias) {
        return isCoherent(sysMenuRepository.findByAlias(alias), id);
    }

}
