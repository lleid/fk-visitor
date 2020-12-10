package com.fk.visitor.api.controller.team.beans;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Team;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "id", direction = Direction.ASC)})
public class TeamQuery extends JpaQuery<Team> {
    @ApiModelProperty("公司")
    @QueryProperty(column = "company", expression = Expression.LIKE)
    public String company;

    @ApiModelProperty("开始日期")
    @QueryProperty(column = "orderAt", expression = Expression.GTE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date from;

    @ApiModelProperty("结束日期")
    @QueryProperty(column = "orderAt", expression = Expression.LTE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date to;

    @ApiModelProperty(value = "用户Id")
    @QueryProperty(column = "operator.id", expression = Expression.EQ)
    private String operatorId;

    @ApiModelProperty("邀请码")
    @QueryProperty(column = "inviteCode", expression = Expression.EQ)
    public String inviteCode;
}
