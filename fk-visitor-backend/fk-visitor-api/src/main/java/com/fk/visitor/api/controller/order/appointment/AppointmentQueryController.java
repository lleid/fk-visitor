package com.fk.visitor.api.controller.order.appointment;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.order.appointment.query.AppointmentQuery;
import com.fk.visitor.api.utils.DateUtils;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.order.Appointment;
import com.fk.visitor.lib.entity.system.Operator;
import com.fk.visitor.lib.repository.order.AppointmentRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/appointment")
@Api(tags = {"预约"}, description = "/appointment")
public class AppointmentQueryController extends BaseJpaQueryController<Appointment, Long, AppointmentQuery> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    protected ModelRepository<Appointment, Long> getRepository() {
        return appointmentRepository;
    }

    @Override
    protected void handleQuery(AppointmentQuery query, Principal principal) {
        Operator operator = OperatorUtils.parse(principal);
        query.setOperatorId(operator.getId());
    }

    @RequestMapping(value = "/query/invitecode")
    @ResponseBody
    public Appointment queryByInviteCode(String inviteCode, Principal principal) {
        Date currentDate = DateUtils.getCurrentDate();

        List<Appointment> appointmentList = appointmentRepository.findByOrderAtAndInviteCode(currentDate, inviteCode);

        if (appointmentList != null && appointmentList.size() > 0) {
            if (appointmentList.size() == 1 && appointmentList.get(0).getIsCame()) {
                throw new InvalidParamException("邀请码已使用，请联系前台人员");
            }
            return appointmentList.get(0);
        }
        return null;
    }
}
