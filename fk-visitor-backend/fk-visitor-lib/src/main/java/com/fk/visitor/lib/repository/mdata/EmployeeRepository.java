package com.fk.visitor.lib.repository.mdata;

import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.mdata.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends ModelRepository<Employee, Long> {
    Employee findByName(String name);

    Employee findByEnName(String enName);

    Employee findByDepartment(String name);
}
