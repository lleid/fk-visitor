package com.fk.visitor.api.controller.mdata.protocol;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.mdata.protocol.beans.ProtocolQuery;
import com.fk.visitor.lib.entity.mdata.Protocol;
import com.fk.visitor.lib.repository.mdata.ProtocolRepository;
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

    @RequestMapping(value = "/check/name")
    public Boolean checkName(Long id, String name) {
        return isCoherent(protocolRepository.findByName(name), id);
    }

}
