package com.fk.visitor.api.controller.signoutreason;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.SignOutReason;
import com.fk.visitor.lib.repository.SignOutReasonRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/signOutReason")
@Api(tags = {"签出原因"})
public class SignOutReasonCRUDController extends BaseModelCRUDController<SignOutReason, Long> {

    @Autowired
    private SignOutReasonRepository signOutReasonRepository;

    @Override
    protected ModelRepository<SignOutReason, Long> getRepository() {
        return signOutReasonRepository;
    }

    @Override
    protected SignOutReason handleCreate(SignOutReason model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected SignOutReason handleUpdate(SignOutReason model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected SignOutReason handlePatch(SignOutReason model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
