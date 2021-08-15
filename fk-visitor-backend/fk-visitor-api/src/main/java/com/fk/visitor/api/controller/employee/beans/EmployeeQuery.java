package com.fk.visitor.api.controller.employee.beans;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Employee;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "id", direction = Direction.ASC)})
public class EmployeeQuery extends JpaQuery<Employee> {
    @ApiModelProperty("姓名")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;

    @ApiModelProperty("英文名")
    @QueryProperty(column = "enName", expression = Expression.LIKE)
    public String enName;

    @ApiModelProperty("部门")
    @QueryProperty(column = "department", expression = Expression.LIKE)
    public String department;
}
