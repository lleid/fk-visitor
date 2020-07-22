package com.fk.visitor.api.controller.global.req;

import cn.kinkii.novice.framework.controller.request.GenericRequest;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fk.visitor.api.entity.Operator;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;

@Setter
@Getter
public class RegisterReq extends GenericRequest<Operator> {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @Transient
    @ApiModelProperty(value = "密码原文（提交用）")
    private String plainPassword;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "手机")
    private String mobile;
}
