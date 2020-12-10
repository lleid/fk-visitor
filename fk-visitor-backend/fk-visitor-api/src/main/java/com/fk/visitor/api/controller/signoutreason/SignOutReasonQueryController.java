package com.fk.visitor.api.controller.signoutreason;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.signoutreason.beans.SignOutReasonQuery;
import com.fk.visitor.lib.entity.SignOutReason;
import com.fk.visitor.lib.repository.SignOutReasonRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signoutreason")
@Api(tags = {"签出原因"}, description = "/signoutreason")
public class SignOutReasonQueryController extends BaseJpaQueryController<SignOutReason, Long, SignOutReasonQuery> {

    @Autowired
    private SignOutReasonRepository signOutReasonRepository;

    @Override
    protected ModelRepository<SignOutReason, Long> getRepository() {
        return signOutReasonRepository;
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(Long id, String name) {
        return isCoherent(signOutReasonRepository.findByName(name), id);
    }

}
