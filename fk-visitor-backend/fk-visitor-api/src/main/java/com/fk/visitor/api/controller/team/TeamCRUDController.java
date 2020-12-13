package com.fk.visitor.api.controller.team;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.team.beans.Staff;
import com.fk.visitor.api.utils.OperatorUtils;
import com.fk.visitor.lib.entity.Appointment;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.entity.Team;
import com.fk.visitor.lib.repository.AppointmentRepository;
import com.fk.visitor.lib.repository.TeamRepository;
import io.swagger.annotations.Api;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/team")
@Api(tags = {"员工管理"})
public class TeamCRUDController extends BaseModelCRUDController<Team, Long> {

    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    @Override
    protected ModelRepository<Team, Long> getRepository() {
        return teamRepository;
    }

    @Override
    protected Team handleCreate(Team model, Principal principal, HttpServletRequest request) {
        Operator operator = OperatorUtils.parse(principal);
        model.setInviteCode(RandomStringUtils.randomNumeric(6).toUpperCase());
        model.setOperator(operator);
        return model;
    }

    @Override
    protected Team handleUpdate(Team model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Team handlePatch(Team model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @RequestMapping(value = "/staff/{id}", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult staffCreate(@PathVariable Long id, Staff staff, Principal principal, HttpServletRequest request) {
        Team team = teamRepository.findById(id).orElseThrow(() -> new InvalidParamException("参数异常"));
        Operator operator = OperatorUtils.parse(principal);

        Appointment appointment = new Appointment();
        appointment.setName(staff.getName());
        appointment.setInterviewer(team.getInterviewer());
        appointment.setOrderAt(team.getOrderAt());
        appointment.setMobile(staff.getMobile());
        appointment.setIdCard(staff.getIdCard());
        appointment.setCompany(team.getCompany());
        appointment.setTitle(staff.getTitle());
        appointment.setPurpose(team.getPurpose());
        appointment.setVisitArea(team.getVisitArea());
        appointment.setIsTeam(true);
        appointment.setIsMessage(staff.getIsMessage());

        appointment.setOperator(operator);
        appointment.setInviteCode(team.getInviteCode());

        appointment.setCreateUsername(operator.getUsername());
        appointment.setTeam(team);

        appointmentRepository.create(appointment);
        return BaseResult.success("创建成功");
    }
}
