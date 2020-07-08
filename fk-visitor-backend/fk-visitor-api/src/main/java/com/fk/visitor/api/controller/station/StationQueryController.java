package com.fk.visitor.api.controller.station;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.station.query.StationQuery;
import com.fk.visitor.lib.entity.Station;
import com.fk.visitor.lib.repository.StationRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/station")
@Api(tags = {"站点"}, description = "/station")
public class StationQueryController extends BaseJpaQueryController<Station, Long, StationQuery> {

    @Autowired
    private StationRepository stationRepository;

    @Override
    protected ModelRepository<Station, Long> getRepository() {
        return stationRepository;
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(Long id, String name) {
        return isCoherent(stationRepository.findByName(name), id);
    }
}
