package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.LogicalDeleteable;
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
public class Appointment extends PkNativeID implements LogicalDeleteable {

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

    @Column(name = "title", length = 32)
    @ApiModelProperty(value = "职务")
    private String title;

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

    @Column(name = "is_message", length = 1)
    @ApiModelProperty(value = "是否发送短信")
    private Boolean isMessage = false;

    @Column(name = "is_team", length = 1)
    @ApiModelProperty(value = "是否团队")
    private Boolean isTeam = false;

    @ApiModelProperty("创建时间")
    @Column(name = "create_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createAt=new Date();

    @ApiModelProperty(value = "创建人")
    @Column(name = "create_username", length = 16)
    private String createUsername;

    @ApiModelProperty("最后更改时间")
    @Column(name = "update_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateAt;

    @Column(name = "update_username", length = 16)
    @ApiModelProperty(value = "最后更改人")
    private String updateUsername;

    @ApiModelProperty("作废时间")
    @Column(name = "delete_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date deleteAt;

    @Column(name = "delete_username", length = 16)
    @ApiModelProperty(value = "作废人")
    private String deleteUsername;

    @ApiModelProperty("是否删除")
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "operator_id")
    @ApiModelProperty("用户Id")
    private Operator operator;

    @ApiModelProperty("团队")
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "team_id")
    @JsonIgnore
    private Team team;

    @Override
    public String getDelFlag() {
        return "isDeleted";
    }

    @Override
    public String getDelTimeFlag() {
        return "deleteAt";
    }
}
