package com.fk.visitor.api.controller.mdata.protocol;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.mdata.Protocol;
import com.fk.visitor.lib.repository.mdata.ProtocolRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/protocol")
@Api(tags = {"协议"})
public class ProtocolCRUDController extends BaseModelCRUDController<Protocol, Long> {

    @Autowired
    private ProtocolRepository protocolRepository;

    @Override
    protected ModelRepository<Protocol, Long> getRepository() {
        return protocolRepository;
    }

    @Override
    protected Protocol handleCreate(Protocol model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Protocol handleUpdate(Protocol model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Protocol handlePatch(Protocol model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
