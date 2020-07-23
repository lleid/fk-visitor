package com.fk.visitor.api.controller.appointment;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.accessory.CloopenOrderSmsSender;
import com.fk.visitor.api.conf.SmsConfig;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.Appointment;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.entity.Order;
import com.fk.visitor.lib.repository.AppointmentRepository;
import com.fk.visitor.lib.repository.OrderRepository;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping("/appointment")
@Api(tags = {"预约"})
public class AppointmentCRUDController extends BaseModelCRUDController<Appointment, Long> {

    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CloopenOrderSmsSender cloopenOrderSmsSender;
    @Autowired
    private SmsConfig smsConfig;

    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    protected ModelRepository<Appointment, Long> getRepository() {
        return appointmentRepository;
    }

    @Override
    protected Appointment handleCreate(Appointment model, Principal principal, HttpServletRequest request) {
        Operator operator = OperatorUtils.parse(principal);
        model.setInviteCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
        model.setOperator(operator);
        return model;
    }

    @Override
    protected void handleAfterCreate(Appointment model, Principal principal) {
        String orderAt = sdf.format(model.getOrderAt());
        cloopenOrderSmsSender.send(model.getMobile(), smsConfig.getT1(), model.getName(), orderAt, model.getInviteCode());
    }

    @Override
    protected Appointment handleUpdate(Appointment model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Appointment handlePatch(Appointment model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @RequestMapping(value = "/visit/{id}")
    @ResponseBody
    public BaseResult visit(@PathVariable Long id, Principal principal) {
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new InvalidParamException("参数异常"));

        Order model = new Order();

        BeanUtils.copyProperties(appointment, model);

        model.setId(null);
        model.setVisitAt(new Date());
        model.setOrderType(Order.APPOINTMENT);
        model.setPurpose(appointment.getPurpose());
        orderRepository.create(model);

        appointment.setIsCame(true);
        appointmentRepository.update(appointment);
        return BaseResult.success("操作成功");
    }
}
