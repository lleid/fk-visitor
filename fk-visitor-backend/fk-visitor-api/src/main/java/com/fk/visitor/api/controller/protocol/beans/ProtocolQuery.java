package com.fk.visitor.api.controller.protocol.beans;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Protocol;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "id", direction = Direction.ASC)})
public class ProtocolQuery extends JpaQuery<Protocol> {
    @ApiModelProperty("类型")
    @QueryProperty(column = "type", expression = Expression.EQ)
    public String type;
}
