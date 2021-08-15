package com.fk.visitor.api.controller.mdata.purpose;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.mdata.Purpose;
import com.fk.visitor.lib.repository.mdata.PurposeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/purpose")
@Api(tags = {"事由"})
public class PurposeCRUDController extends BaseModelCRUDController<Purpose, Long> {

    @Autowired
    private PurposeRepository purposeRepository;

    @Override
    protected ModelRepository<Purpose, Long> getRepository() {
        return purposeRepository;
    }

    @Override
    protected Purpose handleCreate(Purpose model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Purpose handleUpdate(Purpose model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Purpose handlePatch(Purpose model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
