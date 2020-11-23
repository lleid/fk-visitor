package com.fk.visitor.api.controller.order;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuerySpecification;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.order.query.OrderQuery;
import com.fk.visitor.api.utils.ApiConstants;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.entity.Order;
import com.fk.visitor.lib.repository.OrderRepository;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
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

    @RequestMapping(value = "/group/week", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> groupWeek(Principal principal) {
        Date now = ApiConstants.getCurrentDate();
        Date firstDate = ApiConstants.getWeekDFirstate(now);
        Date endDate = ApiConstants.getWeekEndDate(now);
//        if (now.compareTo(endDate) <= 0) {
//            endDate = now;
//        }

        List<Integer> list = Lists.newArrayList();

        while (firstDate.compareTo(endDate) <= 0) {
            Date from = firstDate;
            Date to = DateUtils.addDays(firstDate, 1);

            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            if (orders == null) {
                list.add(0);
            } else {
                list.add(orders.size());
            }
            firstDate = DateUtils.addDays(firstDate, 1);
        }

        return list;
    }
}
