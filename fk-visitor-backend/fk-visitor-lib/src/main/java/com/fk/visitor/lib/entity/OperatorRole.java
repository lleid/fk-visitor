package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel(value = "管理用户角色", description = "管理用户角色")
@Entity
@Table(name = "t_operator_role")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class OperatorRole extends PkNativeID {

    @ApiModelProperty(value = "角色名称")
    @Column(name = "name", nullable = false, length = 16)
    private String name;

    @ApiModelProperty(value = "角色Key")
    @Column(name = "key_code", nullable = false, length = 16, unique = true)
    private String keyCode;

    @ApiModelProperty(value = "是否超级管理员")
    @Column(name = "is_supervisor", nullable = false)
    private Boolean isSupervisor = false;

}
