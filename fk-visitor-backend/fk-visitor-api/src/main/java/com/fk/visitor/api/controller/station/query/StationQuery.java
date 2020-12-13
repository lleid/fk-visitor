package com.fk.visitor.api.controller.station.query;

import cn.kinkii.novice.framework.controller.query.Direction;
import cn.kinkii.novice.framework.controller.query.Expression;
import cn.kinkii.novice.framework.controller.query.annotations.OrderProperty;
import cn.kinkii.novice.framework.controller.query.annotations.QueryClass;
import cn.kinkii.novice.framework.controller.query.annotations.QueryProperty;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuery;
import com.fk.visitor.lib.entity.Station;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@QueryClass(orders = {@OrderProperty(column = "name", direction = Direction.ASC)})
public class StationQuery extends JpaQuery<Station> {

    @ApiModelProperty(value = "名称")
    @QueryProperty(column = "name", expression = Expression.LIKE)
    public String name;

    @ApiModelProperty(value = "是否删除")
    @QueryProperty(column = "isDeleted", expression = Expression.EQ)
    public Boolean isDeleted = false;
}
