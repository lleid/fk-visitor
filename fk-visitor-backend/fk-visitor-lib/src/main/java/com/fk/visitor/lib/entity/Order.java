package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fk.visitor.lib.entity.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@ApiModel(value = "访客记录", description = "访客记录")
@Entity
@Table(name = "t_order")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Order extends PkNativeID {

    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "gender", length = 8)
    @ApiModelProperty(value = "性别")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty(value = "拜访时间")
    @Column(name = "visit_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date visitAt = new Date();

    @ApiModelProperty(value = "签出时间")
    @Column(name = "sign_out_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signOutAt;

    @Column(name = "mobile", length = 16)
    @ApiModelProperty(value = "手机")
    private String mobile;

    @Column(name = "id_card", length = 32)
    @ApiModelProperty(value = "身份证号")
    private String idCard;

    @Column(name = "email", length = 64)
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @Column(name = "company", length = 32)
    @ApiModelProperty(value = "公司")
    private String company;

    @Column(name = "job", length = 32)
    @ApiModelProperty(value = "职务")
    private String job;

    @Column(name = "purpose", length = 32)
    @ApiModelProperty(value = "拜访事由")
    private String purpose;

    @Column(name = "avatar", length = 128)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "station_id")
    @ApiModelProperty("位置")
    private Station station;

    @Column(name = "is_sign_out", length = 1)
    @ApiModelProperty(value = "是否登出")
    private Boolean isSignOut = false;
}
