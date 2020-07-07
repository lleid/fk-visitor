package com.fk.visitor.api.controller.station;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.repository.OperatorRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/operator")
@Api(tags = {"账号"})
public class StationCRUDController extends BaseModelCRUDController<Operator, String> {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    protected ModelRepository<Operator, String> getRepository() {
        return operatorRepository;
    }

    @Override
    protected Operator handleCreate(Operator model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Operator handleUpdate(Operator model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Operator handlePatch(Operator model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
