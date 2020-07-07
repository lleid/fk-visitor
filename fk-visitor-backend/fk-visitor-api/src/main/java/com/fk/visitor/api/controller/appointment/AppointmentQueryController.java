package com.fk.visitor.api.controller.appointment;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.appointment.query.AppointmentQuery;
import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.Appointment;
import com.fk.visitor.lib.repository.AppointmentRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

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
        if (operator.getStation() != null) {
            query.setStationId(operator.getStation().getId());
        }
    }
}
