package com.fk.visitor.api.controller.operatorrole.query;

import com.fk.visitor.api.entity.OperatorRole;
import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "id", direction = Direction.ASC)})
public class OperatorRoleQuery extends JpaQuery<OperatorRole> {

    @ApiModelProperty("keyCode")
    @QueryProperty(column = "keyCode", expression = Expression.LIKE)
    public String keyCode;

    @ApiModelProperty(value = "别名")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;
}
