package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ApiModel(value = "团队", description = "团队")
@Entity
@Table(name = "t_team")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Team extends PkNativeID {

    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "amount", length = 32)
    @ApiModelProperty(value = "人数")
    private Integer amount;

    @Column(name = "interviewer", length = 32)
    @ApiModelProperty(value = "受访人")
    private String interviewer;

    @ApiModelProperty(value = "预约时间")
    @Column(name = "order_at", nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date orderAt;

    @Column(name = "company", length = 32)
    @ApiModelProperty(value = "公司")
    private String company;

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

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "operator_id")
    @ApiModelProperty("用户Id")
    private Operator operator;

    @ApiModelProperty("预约单")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "team", fetch = FetchType.EAGER)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("createAt desc")
    private List<Appointment> appointmentList;
}
