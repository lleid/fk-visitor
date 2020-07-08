package com.fk.visitor.api.controller.station;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Station;
import com.fk.visitor.lib.repository.StationRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/station")
@Api(tags = {"站点"})
public class StationCRUDController extends BaseModelCRUDController<Station, Long> {

    @Autowired
    private StationRepository stationRepository;

    @Override
    protected ModelRepository<Station, Long> getRepository() {
        return stationRepository;
    }

    @Override
    protected Station handleCreate(Station model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Station handleUpdate(Station model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Station handlePatch(Station model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
