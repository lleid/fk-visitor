package com.fk.visitor.api.controller.order;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuerySpecification;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.order.query.OrderQuery;
import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.Order;
import com.fk.visitor.lib.repository.OrderRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = {"访客记录"}, description = "/order")
public class OrderQueryController extends BaseJpaQueryController<Order, Long, OrderQuery> {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    protected ModelRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    @Override
    protected void handleQuery(OrderQuery query, Principal principal) {
        Operator operator = OperatorUtils.parse(principal);
        if (operator.getStation() != null) {
            query.setStationId(operator.getStation().getId());
        }
    }

    @RequestMapping(value = "/query/history", method = RequestMethod.GET)
    @ResponseBody
    public Order history(String mobile, Principal principal) {
        OrderQuery query = new OrderQuery();
        query.setMobile(mobile);
        List<Order> list = orderRepository.findAll(new JpaQuerySpecification<>(query));

        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
