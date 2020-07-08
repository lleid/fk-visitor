package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fk.visitor.lib.entity.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@ApiModel(value = "预约", description = "预约")
@Entity
@Table(name = "t_appointment")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Appointment extends PkNativeID {

    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "gender", length = 8)
    @ApiModelProperty(value = "性别")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @ApiModelProperty(value = "预约时间")
    @Column(name = "order_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderAt;

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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "purpose_id")
    @ApiModelProperty(value = "拜访事由")
    private Purpose purpose;

    @Column(name = "invite_code", length = 8)
    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @Column(name = "is_came", length = 1)
    @ApiModelProperty(value = "是否到达")
    private Boolean isCame = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "station_id")
    @ApiModelProperty("位置")
    private Station station;
}
