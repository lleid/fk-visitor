package com.fk.visitor.api.controller.mdata.employee;

import cn.kinkii.novice.framework.controller.BaseJpaQueryController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.api.controller.mdata.employee.beans.EmployeeQuery;
import com.fk.visitor.lib.entity.mdata.Employee;
import com.fk.visitor.lib.repository.mdata.EmployeeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@Api(tags = {"协议"}, description = "/employee")
public class EmployeeQueryController extends BaseJpaQueryController<Employee, Long, EmployeeQuery> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    protected ModelRepository<Employee, Long> getRepository() {
        return employeeRepository;
    }

    @RequestMapping(value = "/check/name")
    public Boolean checkName(Long id, String name) {
        return isCoherent(employeeRepository.findByName(name), id);
    }

}
