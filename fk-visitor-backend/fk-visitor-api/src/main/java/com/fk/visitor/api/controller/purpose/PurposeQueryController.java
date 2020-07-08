package com.fk.visitor.api.controller.purpose;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.purpose.query.PurposeQuery;
import com.fk.visitor.lib.entity.Purpose;
import com.fk.visitor.lib.repository.PurposeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/purpose")
@Api(tags = {"事由"}, description = "/purpose")
public class PurposeQueryController extends BaseJpaQueryController<Purpose, Long, PurposeQuery> {

    @Autowired
    private PurposeRepository purposeRepository;

    @Override
    protected ModelRepository<Purpose, Long> getRepository() {
        return purposeRepository;
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(Long id, String name) {
        return isCoherent(purposeRepository.findByName(name), id);
    }
}
