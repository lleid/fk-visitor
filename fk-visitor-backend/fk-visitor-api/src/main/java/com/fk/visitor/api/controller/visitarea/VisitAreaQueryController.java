package com.fk.visitor.api.controller.visitarea;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.visitarea.query.VisitAreaQuery;
import com.fk.visitor.lib.entity.VisitArea;
import com.fk.visitor.lib.repository.VisitAreaRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/visit/area")
@Api(tags = {"参观区域"}, description = "/visit/area")
public class VisitAreaQueryController extends BaseJpaQueryController<VisitArea, Long, VisitAreaQuery> {

    @Autowired
    private VisitAreaRepository visitAreaRepository;

    @Override
    protected ModelRepository<VisitArea, Long> getRepository() {
        return visitAreaRepository;
    }

    @RequestMapping(value = "/check/cnname")
    public Boolean checkCnName(Long id, String name) {
        return isCoherent(visitAreaRepository.findByCnName(name), id);
    }

    @RequestMapping(value = "/check/enname")
    public Boolean checkEnName(Long id, String name) {
        return isCoherent(visitAreaRepository.findByEnName(name), id);
    }
}
