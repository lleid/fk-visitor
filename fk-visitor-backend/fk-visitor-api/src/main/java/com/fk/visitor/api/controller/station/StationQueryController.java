package com.fk.visitor.api.controller.station;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.operator.query.OperatorQuery;
import com.fk.visitor.api.entity.Operator;
import com.fk.visitor.api.repository.OperatorRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operator")
@Api(tags = {"账号"}, description = "/operator")
public class StationQueryController extends BaseJpaQueryController<Operator, String, OperatorQuery> {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    protected ModelRepository<Operator, String> getRepository() {
        return operatorRepository;
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(String id, String name) {
        return isCoherent(operatorRepository.findByName(name), id);
    }
}
