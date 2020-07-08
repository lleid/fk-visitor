package com.fk.visitor.api.controller.appointment;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Appointment;
import com.fk.visitor.lib.repository.AppointmentRepository;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/appointment")
@Api(tags = {"预约"})
public class AppointmentCRUDController extends BaseModelCRUDController<Appointment, Long> {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    protected ModelRepository<Appointment, Long> getRepository() {
        return appointmentRepository;
    }

    @Override
    protected Appointment handleCreate(Appointment model, Principal principal, HttpServletRequest request) {
        model.setInviteCode(RandomStringUtils.randomAlphanumeric(6).toUpperCase());
        return model;
    }

    @Override
    protected Appointment handleUpdate(Appointment model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Appointment handlePatch(Appointment model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
