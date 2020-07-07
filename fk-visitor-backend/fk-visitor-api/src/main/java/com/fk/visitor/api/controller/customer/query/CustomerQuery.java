package com.fk.visitor.api.controller.customer.query;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Customer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "name", direction = Direction.DESC)})
public class CustomerQuery extends JpaQuery<Customer> {

    @ApiModelProperty("手机号")
    @QueryProperty(column = "mobile", expression = Expression.LIKE)
    public String mobile;

    @ApiModelProperty(value = "姓名")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;
}
