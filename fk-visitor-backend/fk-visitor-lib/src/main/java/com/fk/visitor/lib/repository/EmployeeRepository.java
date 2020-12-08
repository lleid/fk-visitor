package com.fk.visitor.lib.repository;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ModelRepository<Employee, Long> {
    Employee findByName(String name);

    Employee findByDepartment(String name);
}
