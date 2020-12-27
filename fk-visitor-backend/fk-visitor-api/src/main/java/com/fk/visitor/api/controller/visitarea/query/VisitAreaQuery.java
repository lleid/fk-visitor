package com.fk.visitor.api.controller.visitarea.query;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.VisitArea;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "cnName", direction = Direction.ASC)})
public class VisitAreaQuery extends JpaQuery<VisitArea> {

    @ApiModelProperty(value = "中文名称")
    @QueryProperty(column = "cnName", expression = Expression.LIKE)
    public String cnName;

    @ApiModelProperty(value = "英文名称")
    @QueryProperty(column = "enName", expression = Expression.LIKE)
    public String enName;

    @ApiModelProperty(value = "是否删除")
    @QueryProperty(column = "isDeleted", expression = Expression.EQ)
    public Boolean isDeleted;
}
