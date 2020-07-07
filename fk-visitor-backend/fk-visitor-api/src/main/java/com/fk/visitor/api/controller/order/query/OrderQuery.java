package com.fk.visitor.api.controller.order.query;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "visitAt", direction = Direction.ASC)})
public class OrderQuery extends JpaQuery<Order> {

    @ApiModelProperty("手机号")
    @QueryProperty(column = "mobile", expression = Expression.LIKE)
    public String mobile;

    @ApiModelProperty(value = "姓名")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;

    @ApiModelProperty(value = "公司")
    @QueryProperty(column = "company", expression = Expression.LIKE)
    private String company;

    @ApiModelProperty(value = "站点")
    @QueryProperty(column = "station.id", expression = Expression.EQ)
    private Long stationId;
}
