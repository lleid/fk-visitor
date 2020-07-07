package com.fk.visitor.api.entity;

import cn.kinkii.novice.framework.entity.AssignableID;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "t_sys_role")
@Getter
@Setter
public class SysRole extends AssignableID<String> {

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_sys_role_permission_map", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "permission_id")})
    private Set<SysPermission> permissions;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_sys_role_menu_map", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    @OrderBy("code ASC")
    private Set<SysMenu> menus;

}
