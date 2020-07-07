package com.fk.visitor.api.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_sys_permission")
@Getter
@Setter
@NoArgsConstructor
public class SysPermission extends PkNativeID {

    public SysPermission (String url) {
        this.url = url;
        this.method = "*";
    }

    @ApiModelProperty(value = "可访问url[pattern]")
    @Column(name = "url", nullable = false)
    private String url;

    @ApiModelProperty(value = "http方法")
    @Column(name = "method", length = 16)
    private String method;

}
