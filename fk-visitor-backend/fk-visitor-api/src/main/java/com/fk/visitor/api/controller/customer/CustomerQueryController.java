package com.fk.visitor.api.controller.customer;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.customer.query.CustomerQuery;
import com.fk.visitor.lib.entity.Customer;
import com.fk.visitor.lib.repository.CustomerRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customer")
@Api(tags = {"账号"}, description = "/customer")
public class CustomerQueryController extends BaseJpaQueryController<Customer, String, CustomerQuery> {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    protected ModelRepository<Customer, String> getRepository() {
        return customerRepository;
    }
}
