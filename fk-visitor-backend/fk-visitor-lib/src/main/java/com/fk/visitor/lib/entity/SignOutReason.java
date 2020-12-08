package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel(value = "退出原因", description = "退出原因")
@Entity
@Table(name = "t_sign_out_reason")
@Getter
@Setter
@NoArgsConstructor
public class SignOutReason extends PkNativeID {

    @ApiModelProperty(value = "名称")
    @Column(name = "name ", length = 128)
    private String name;
}
