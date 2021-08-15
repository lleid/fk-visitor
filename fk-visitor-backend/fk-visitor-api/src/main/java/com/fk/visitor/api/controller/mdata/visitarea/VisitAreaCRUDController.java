package com.fk.visitor.api.controller.mdata.visitarea;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.mdata.VisitArea;
import com.fk.visitor.lib.repository.mdata.VisitAreaRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/visit/area")
@Api(tags = {"事由"})
public class VisitAreaCRUDController extends BaseModelCRUDController<VisitArea, Long> {

    @Autowired
    private VisitAreaRepository visitAreaRepository;

    @Override
    protected ModelRepository<VisitArea, Long> getRepository() {
        return visitAreaRepository;
    }

    @Override
    protected VisitArea handleCreate(VisitArea model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected VisitArea handleUpdate(VisitArea model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected VisitArea handlePatch(VisitArea model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
