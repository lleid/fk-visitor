package com.fk.visitor.api.controller.customer;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Customer;
import com.fk.visitor.lib.repository.CustomerRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/customer")
@Api(tags = {"客户"})
public class CustomerCRUDController extends BaseModelCRUDController<Customer, String> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    protected ModelRepository<Customer, String> getRepository() {
        return customerRepository;
    }

    @Override
    protected Customer handleCreate(Customer model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Customer handleUpdate(Customer model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Customer handlePatch(Customer model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
