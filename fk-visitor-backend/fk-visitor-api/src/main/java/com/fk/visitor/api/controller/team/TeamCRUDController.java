package com.fk.visitor.api.controller.team;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Team;
import com.fk.visitor.lib.repository.TeamRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/team")
@Api(tags = {"员工管理"})
public class TeamCRUDController extends BaseModelCRUDController<Team, Long> {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    protected ModelRepository<Team, Long> getRepository() {
        return teamRepository;
    }

    @Override
    protected Team handleCreate(Team model, Principal principal, HttpServletRequest request) {
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
}
