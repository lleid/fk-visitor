package com.fk.visitor.api.controller.protocol;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.protocol.beans.ProtocolQuery;
import com.fk.visitor.lib.entity.Protocol;
import com.fk.visitor.lib.repository.ProtocolRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/protocol")
@Api(tags = {"协议"}, description = "/protocol")
public class ProtocolQueryController extends BaseJpaQueryController<Protocol, Long, ProtocolQuery> {

    @Autowired
    private ProtocolRepository protocolRepository;

    @Override
    protected ModelRepository<Protocol, Long> getRepository() {
        return protocolRepository;
    }

    @RequestMapping(value = "/check/type")
    public Boolean checkType(Long id, String type) {
        return isCoherent(protocolRepository.findByType(type), id);
    }

}
