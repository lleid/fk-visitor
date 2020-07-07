package com.fk.visitor.api.controller.operator.query;

import com.fk.visitor.api.entity.Operator;
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
@QueryClass(orders = {@OrderProperty(column = "username", direction = Direction.ASC)})
public class OperatorQuery extends JpaQuery<Operator> {

    @ApiModelProperty("用户名")
    @QueryProperty(column = "username", expression = Expression.LIKE)
    public String username;

    @ApiModelProperty("手机号")
    @QueryProperty(column = "mobile", expression = Expression.LIKE)
    public String mobile;

    @ApiModelProperty(value = "姓名")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;

    @ApiModelProperty(value = "角色")
    @QueryProperty(column = "roles.keyCode", expression = Expression.EQ)
    private String roleCode;

}
