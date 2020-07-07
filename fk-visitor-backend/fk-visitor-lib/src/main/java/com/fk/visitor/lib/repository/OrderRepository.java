package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Order;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends ModelRepository<Order, Long> {

}
