package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkUUID;
import com.fk.visitor.lib.entity.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@ApiModel(value = "访客", description = "访客")
@Entity
@Table(name = "t_customer")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Customer  extends PkUUID {
    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "gender", length = 8)
    @ApiModelProperty(value = "性别")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "mobile", length = 16, unique = true)
    @ApiModelProperty(value = "手机")
    private String mobile;

    @Column(name = "email", length = 64)
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @Column(name = "company", length = 32)
    @ApiModelProperty(value = "公司")
    private String company;

    @Column(name = "avatar", length = 128)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @Column(name = "id_card", length = 32)
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @Column(name = "job", length = 32)
    @ApiModelProperty(value = "职务")
    private String job;
}
