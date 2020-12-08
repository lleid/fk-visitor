package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column(name = "interviewer", length = 32)
    @ApiModelProperty(value = "受访人")
    private String interviewer;

    @ApiModelProperty(value = "预约时间")
    @Column(name = "order_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderAt;

    @Column(name = "mobile", length = 16)
    @ApiModelProperty(value = "手机")
    private String mobile;

    @Column(name = "id_card", length = 32)
    @ApiModelProperty(value = "证件号")
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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "visit_area_id")
    @ApiModelProperty(value = "参观区域")
    private VisitArea visitArea;

    @Column(name = "invite_code", length = 8)
    @ApiModelProperty(value = "邀请码")
    private String inviteCode;

    @Column(name = "is_came", length = 1)
    @ApiModelProperty(value = "是否到达")
    private Boolean isCame = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "operator_id")
    @ApiModelProperty("用户Id")
    private Operator operator;

    @ApiModelProperty("团队")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;
}
