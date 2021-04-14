package com.fk.visitor.api.controller.order;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.controller.query.jpa.JpaQuerySpecification;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.order.query.OrderQuery;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.*;
import com.fk.visitor.lib.repository.*;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
@Api(tags = {"访客记录"})
public class OrderCRUDController extends BaseModelCRUDController<Order, Long> {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PurposeRepository purposeRepository;
    @Autowired
    private VisitAreaRepository visitAreaRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Value("${project.upload.url}")
    private String FILE_BASE_URL;

    @Value("${project.upload.path}")
    private String UPLOADED_FOLDER;

    private static final String FILE_DATE_FORMAT = "yyyyMMddHHmmss";

    private static final String suffix = ".png";

    @Override
    protected ModelRepository<Order, Long> getRepository() {
        return orderRepository;
    }

    private static String getRandomFileName(String fileSuffix) {
        return DateFormatUtils.format(new Date(), FILE_DATE_FORMAT) + RandomStringUtils.randomAlphabetic(6) + fileSuffix;
    }

    @Override
    protected Order handleCreate(Order model, Principal principal, HttpServletRequest request) {
        Operator operator = OperatorUtils.parse(principal);

        Date to = new Date();
        Date from = DateUtils.addMinutes(to, -5);
        OrderQuery query = new OrderQuery();
        query.setMobile(model.getMobile());
        query.setFrom(from);
        query.setTo(to);

        List<Order> list = orderRepository.findAll(new JpaQuerySpecification<>(query));
        if (list.size() > 0) {
            throw new InvalidParamException("请不要重复访问");
        }

        model.setOrderType(Order.ORDER);

        String purposeId = request.getParameter("purposeId");

        if (StringUtils.isNotBlank(purposeId)) {
            Purpose purpose = purposeRepository.findById(Long.parseLong(purposeId)).orElseThrow(() -> new InvalidParamException("参数异常"));
            model.setPurpose(purpose);
        }

        String visitAreaId = request.getParameter("visitAreaId");

        if (StringUtils.isNotBlank(visitAreaId)) {
            VisitArea visitArea = visitAreaRepository.findById(Long.parseLong(visitAreaId)).orElseThrow(() -> new InvalidParamException("参数异常"));
            model.setVisitArea(visitArea);
        }

        String appointmentId = request.getParameter("appointmentId");

        if (StringUtils.isNotBlank(appointmentId)) {
            Appointment appointment = appointmentRepository.findById(Long.parseLong(appointmentId)).orElseThrow(() -> new InvalidParamException("参数异常"));
            appointment.setIsCame(true);
            appointmentRepository.update(appointment);
            model.setOrderType(Order.APPOINTMENT);
        }

        if (operator.getStation() != null) {
            model.setStation(operator.getStation());
        }

        if (StringUtils.isNotBlank(model.getInterviewer())) {
            Employee employee = employeeRepository.findByName(model.getInterviewer());
            if(employee!=null){
                model.setDepartment(employee.getDepartment());
            }
        }

        return model;
    }


    @Override
    protected void handleAfterCreate(Order model, Principal principal) {

    }

    @RequestMapping(value = "/signout/{id}")
    @ResponseBody
    public BaseResult signOut(@PathVariable Long id, String remark, Principal principal) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new InvalidParamException("参数异常"));

        if (order.getIsSignOut()) {
            throw new InvalidParamException("已签出，请不要重复操作");
        }
        order.setIsSignOut(true);
        order.setSignOutAt(new Date());
        if (StringUtils.isNotBlank(remark)) {
            order.setSignOutType("20");
            order.setSignOutReason(remark);
        } else {
            order.setSignOutType("10");
        }

        orderRepository.update(order);
        return BaseResult.success("操作成功");
    }


    @RequestMapping(value = "/history/{id}")
    @ResponseBody
    public BaseResult history(@PathVariable Long id, Principal principal) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new InvalidParamException("参数异常"));

        Order model = new Order();

        BeanUtils.copyProperties(order, model);

        model.setId(null);
        model.setSignOutAt(null);
        model.setIsSignOut(false);
        model.setVisitAt(new Date());

        orderRepository.create(model);
        return BaseResult.success("操作成功");
    }

    @Override
    protected Order handleUpdate(Order model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Order handlePatch(Order model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
