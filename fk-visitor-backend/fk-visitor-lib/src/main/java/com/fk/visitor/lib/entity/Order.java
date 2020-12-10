package com.fk.visitor.lib.entity;

import cn.kinkii.novice.framework.entity.PkNativeID;
import com.fasterxml.jackson.annotation.JsonFormat;
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

    public static final String APPOINTMENT = "APPOINTMENT";
    public static final String ORDER = "ORDER";

    @Column(name = "order_type", length = 16)
    @ApiModelProperty(value = "订单类型")
    private String orderType;

    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "interviewer", length = 32)
    @ApiModelProperty(value = "受访人")
    private String interviewer;

    @ApiModelProperty(value = "拜访时间")
    @Column(name = "visit_at", nullable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date visitAt = new Date();

    @ApiModelProperty(value = "签出时间")
    @Column(name = "sign_out_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date signOutAt;

    /**
     * 10 自动
     * 20 手动
     */
    @Column(name = "sign_out_type", length = 16)
    @ApiModelProperty(value = "签署类型")
    private String signOutType;

    @Column(name = "sign_out_reason", length = 16)
    @ApiModelProperty(value = "签署原因")
    private String signOutReason;

    @Column(name = "mobile", length = 16)
    @ApiModelProperty(value = "手机")
    private String mobile;

    @Column(name = "id_card", length = 32)
    @ApiModelProperty(value = "证件号")
    private String idCard;

    @Column(name = "company", length = 32)
    @ApiModelProperty(value = "公司")
    private String company;

    @Column(name = "department", length = 32)
    @ApiModelProperty(value = "职务")
    private String department;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "purpose_id")
    @ApiModelProperty(value = "拜访事由")
    private Purpose purpose;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "visit_area_id")
    @ApiModelProperty(value = "参观区域")
    private VisitArea visitArea;

    @Column(name = "avatar", length = 128)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "station_id")
    @ApiModelProperty("位置")
    private Station station;

    @Column(name = "is_sign_out", length = 1)
    @ApiModelProperty(value = "是否签出")
    private Boolean isSignOut = false;
}
