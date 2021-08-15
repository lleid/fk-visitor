package com.fk.visitor.api.controller.order;

import cn.kinkii.novice.framework.controller.query.jpa.JpaQuerySpecification;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.builder.ExcelWriterSheetBuilder;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.fk.visitor.api.controller.order.beans.OrderExportBean;
import com.fk.visitor.api.controller.order.beans.OrderQuery;
import com.fk.visitor.lib.entity.order.Order;
import com.fk.visitor.lib.repository.order.OrderRepository;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = {"订单导出"})
public class OrderExportController {

    @Autowired
    private OrderRepository orderRepository;

    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping(value = "/export")
    public void export(OrderQuery query, HttpServletResponse response) {
        List<Order> orderList = orderRepository.findAll(new JpaQuerySpecification<>(query));
        // 获取ExcelWriterSheetBuilder对象
        ExcelWriterSheetBuilder excelWriterSheetBuilder = EasyExcel.writerSheet("订单").automaticMergeHead(false);
        List<OrderExportBean> orderExportBeans = genOrderExportBeans(orderList);

        try {
            WriteSheet writeSheet = excelWriterSheetBuilder.build();

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            String fileName = URLEncoder.encode("订单", "UTF-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");
            ExcelWriter excelWriter = EasyExcel.write(response.getOutputStream(), OrderExportBean.class).build();
            excelWriter.write(orderExportBeans, writeSheet);
            excelWriter.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<OrderExportBean> genOrderExportBeans(List<Order> orderList) {
        List<OrderExportBean> list = Lists.newArrayList();
        orderList.forEach(order -> {
            OrderExportBean bean = new OrderExportBean();
            bean.setName(order.getName());
            bean.setMobile(order.getMobile());
            bean.setVisitAt(DATE_TIME_FORMAT.format(order.getVisitAt()));
            bean.setIdCard(order.getIdCard());
            bean.setCompany(order.getCompany());
            bean.setTitle(order.getTitle());
            if (order.getPurpose() != null) {
                bean.setPurpose(order.getPurpose().getCnName());
            }
            if (order.getVisitArea() != null) {
                bean.setVisitArea(order.getVisitArea().getCnName());
            }
            if (order.getSignOutAt() != null) {
                bean.setSignOutAt(DATE_TIME_FORMAT.format(order.getSignOutAt()));
            }
            list.add(bean);
        });
        return list;
    }
}
