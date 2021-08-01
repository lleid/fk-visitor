package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.beans.Pie;
import com.fk.visitor.lib.entity.Order;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends ModelRepository<Order, Long> {

    @Query("select new com.fk.visitor.lib.beans.Pie(department,count(*)) from Order group by department")
    List<Pie> groupByDepartment();

    Order findByOrderNo(String orderNo);
}
