package com.fk.visitor.api.controller.appointment.query;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Appointment;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "orderAt", direction = Direction.DESC)})
public class AppointmentQuery extends JpaQuery<Appointment> {

    @ApiModelProperty("邀请码")
    @QueryProperty(column = "inviteCode", expression = Expression.EQ)
    public String inviteCode;

    @ApiModelProperty("手机号")
    @QueryProperty(column = "mobile", expression = Expression.LIKE)
    public String mobile;

    @ApiModelProperty(value = "姓名")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;

    @ApiModelProperty(value = "用户Id")
    @QueryProperty(column = "operator.id", expression = Expression.EQ)
    private String operatorId;

    @ApiModelProperty(value = "预约日期")
    @QueryProperty(column = "orderAt", expression = Expression.GTE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date from;

    @ApiModelProperty(value = "预约日期")
    @QueryProperty(column = "orderAt", expression = Expression.LTE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date to;

    @ApiModelProperty(value = "是否团队")
    @QueryProperty(column = "isTeam", expression = Expression.EQ)
    public Boolean isTeam = false;
}
