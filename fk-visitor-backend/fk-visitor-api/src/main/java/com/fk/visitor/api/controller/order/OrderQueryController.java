package com.fk.visitor.api.controller.order;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuerySpecification;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.order.query.OrderQuery;
import com.fk.visitor.api.utils.DateUtils;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.beans.Pie;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.entity.Order;
import com.fk.visitor.lib.repository.OrderRepository;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = {"访客记录"}, description = "/order")
@SuppressWarnings("all")
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

    @RequestMapping(value = "/query/today", method = RequestMethod.GET)
    @ResponseBody
    public List<Order> today(Principal principal) {
        OrderQuery query = new OrderQuery();

        Date from = DateUtils.getCurrentDate();
        Date to = DateUtils.addDays(from, 1);
        query.setFrom(from);
        query.setTo(to);
        List<Order> list = orderRepository.findAll(new JpaQuerySpecification<>(query));
        if (list.size() > 5) {
            return list.subList(0, 5);
        }
        return list;
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

    @RequestMapping(value = "/group/month", method = RequestMethod.GET)
    @ResponseBody
    public List<Pie> groupMonth(Principal principal) {
        Date now = DateUtils.getCurrentDate();
        Date firstDate = DateUtils.getMonthFirstate(now);
        Date endDate = DateUtils.getMonthEndDate(now);
        if (now.compareTo(endDate) <= 0) {
            endDate = now;
        }

        List<Pie> list = Lists.newArrayList();

        while (firstDate.compareTo(endDate) <= 0) {
            Pie pie = new Pie();
            Date from = firstDate;
            Date to = DateUtils.addDays(firstDate, 1);

            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            if (orders == null) {
                pie.setValue(0l);
            } else {
                pie.setValue(new Integer(orders.size()).longValue());
            }
            pie.setName(DateFormatUtils.format(from, "yyyy-MM-dd"));
            list.add(pie);
            firstDate = DateUtils.addDays(firstDate, 1);
        }

        return list;
    }

    @RequestMapping(value = "/group/month1", method = RequestMethod.GET)
    @ResponseBody
    public List<Pie> groupMonth1(Principal principal) {
        Date now = DateUtils.getCurrentDate();
        Date firstDate = DateUtils.getMonthFirstate(now);
        Date endDate = DateUtils.getMonthEndDate(now);
        if (now.compareTo(endDate) <= 0) {
            endDate = now;
        }

        List<Pie> list = Lists.newArrayList();

        while (firstDate.compareTo(endDate) <= 0) {
            Pie pie = new Pie();
            Date from = firstDate;
            Date to = DateUtils.addDays(firstDate, 1);

            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);
            orderQuery.setIsSignOut(true);
            orderQuery.setSignOutType("10");

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            if (orders == null) {
                pie.setValue(0l);
            } else {
                pie.setValue(new Integer(orders.size()).longValue());
            }
            pie.setName(DateFormatUtils.format(from, "yyyy-MM-dd"));
            list.add(pie);
            firstDate = DateUtils.addDays(firstDate, 1);
        }

        return list;
    }

    @RequestMapping(value = "/group/month2", method = RequestMethod.GET)
    @ResponseBody
    public List<Pie> groupMonth2(Principal principal) {
        Date now = DateUtils.getCurrentDate();
        Date firstDate = DateUtils.getMonthFirstate(now);
        Date endDate = DateUtils.getMonthEndDate(now);
        if (now.compareTo(endDate) <= 0) {
            endDate = now;
        }

        List<Pie> list = Lists.newArrayList();

        while (firstDate.compareTo(endDate) <= 0) {
            Pie pie = new Pie();
            Date from = firstDate;
            Date to = DateUtils.addDays(firstDate, 1);

            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);
            orderQuery.setIsSignOut(true);
            orderQuery.setSignOutType("20");

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            if (orders == null) {
                pie.setValue(0l);
            } else {
                pie.setValue(new Integer(orders.size()).longValue());
            }
            pie.setName(DateFormatUtils.format(from, "yyyy-MM-dd"));
            list.add(pie);
            firstDate = DateUtils.addDays(firstDate, 1);
        }

        return list;
    }

    @RequestMapping(value = "/group/month3", method = RequestMethod.GET)
    @ResponseBody
    public List<Pie> groupMonth3(Principal principal) {
        Date now = DateUtils.getCurrentDate();
        Date firstDate = DateUtils.getMonthFirstate(now);
        Date endDate = DateUtils.getMonthEndDate(now);
        if (now.compareTo(endDate) <= 0) {
            endDate = now;
        }

        List<Pie> list = Lists.newArrayList();

        while (firstDate.compareTo(endDate) <= 0) {
            Pie pie = new Pie();
            Date from = firstDate;
            Date to = DateUtils.addDays(firstDate, 1);

            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);
            orderQuery.setIsSignOut(false);

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            pie.setValue(new Integer(orders.size()).longValue());
            pie.setName(DateFormatUtils.format(from, "yyyy-MM-dd"));
            list.add(pie);
            firstDate = DateUtils.addDays(firstDate, 1);
        }

        return list;
    }

    @RequestMapping(value = "/group/quarter", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> groupQuarter(Principal principal) {
        Date currentDate = DateUtils.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        Date from = calendar.getTime();

        List<Integer> list = Lists.newArrayList();
        int i = 0;
        while (i < 12) {
            calendar.add(Calendar.MONTH, 3);
            Date to = calendar.getTime();
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            if (orders == null) {
                list.add(0);
            } else {
                list.add(orders.size());
            }

            i = i + 3;
            from = to;
        }

        return list;
    }

    @RequestMapping(value = "/group/year", method = RequestMethod.GET)
    @ResponseBody
    public List<Integer> groupYear(Principal principal) {
        Date currentDate = DateUtils.getCurrentDate();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(currentDate);

        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 1);

        Date from = calendar.getTime();

        List<Integer> list = Lists.newArrayList();
        int i = 0;
        while (i < 12) {
            calendar.add(Calendar.MONTH, 1);
            Date to = calendar.getTime();
            OrderQuery orderQuery = new OrderQuery();
            orderQuery.setFrom(from);
            orderQuery.setTo(to);

            List<Order> orders = orderRepository.findAll(new JpaQuerySpecification<>(orderQuery));
            if (orders == null) {
                list.add(0);
            } else {
                list.add(orders.size());
            }

            i = i + 1;
            from = to;
        }
        return list;
    }

    @RequestMapping(value = "/group/department", method = RequestMethod.GET)
    @ResponseBody
    public List<Pie> groupDepartment(Principal principal) {
        return orderRepository.groupByDepartment();
    }
}
