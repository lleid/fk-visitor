package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Customer;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends ModelRepository<Customer, String> {
    Customer findByMobile(String mobile);
}
