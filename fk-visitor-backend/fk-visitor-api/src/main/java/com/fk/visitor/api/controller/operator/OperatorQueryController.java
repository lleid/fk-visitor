package com.fk.visitor.api.controller.operator;

import com.fk.visitor.api.controller.operator.query.OperatorQuery;
import com.fk.visitor.lib.entity.Operator;
import com.fk.visitor.lib.repository.OperatorRepository;
import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/operator")
@Api(tags = {"账号"}, description = "/operator")
public class OperatorQueryController extends BaseJpaQueryController<Operator, String, OperatorQuery> {

    @Autowired
    private OperatorRepository operatorRepository;

    @Override
    protected ModelRepository<Operator, String> getRepository() {
        return operatorRepository;
    }

    @RequestMapping(value = "/check/username")
    public Boolean checkUsername(String id, String username) {
        return isCoherent(operatorRepository.findByUsername(username), id);
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(String id, String name) {
        return isCoherent(operatorRepository.findByName(name), id);
    }

    @RequestMapping(value = "/check/mobile")
    public Boolean checkMobile(String id, String mobile) {
        return isCoherent(operatorRepository.findByMobile(mobile), id);
    }

    @RequestMapping(value = "/check/email")
    public Boolean checkEmail(String id, String email) {
        return isCoherent(operatorRepository.findByEmail(email), id);
    }

}
