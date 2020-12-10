package com.fk.visitor.api.controller.team;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.team.beans.TeamQuery;
import com.fk.visitor.lib.entity.Team;
import com.fk.visitor.lib.repository.TeamRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
@Api(tags = {"协议"}, description = "/team")
public class TeamQueryController extends BaseJpaQueryController<Team, Long, TeamQuery> {

    @Autowired
    private TeamRepository teamRepository;

    @Override
    protected ModelRepository<Team, Long> getRepository() {
        return teamRepository;
    }
}
