package com.fk.visitor.api.controller.employee;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.fk.visitor.lib.entity.Employee;
import com.fk.visitor.lib.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("/employee")
@Api(tags = {"员工管理"})
public class EmployeeCRUDController extends BaseModelCRUDController<Employee, Long> {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    protected ModelRepository<Employee, Long> getRepository() {
        return employeeRepository;
    }

    @Override
    protected Employee handleCreate(Employee model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Employee handleUpdate(Employee model, Principal principal, HttpServletRequest request) {
        return model;
    }

    @Override
    protected Employee handlePatch(Employee model, Principal principal, HttpServletRequest request) {
        return model;
    }
}
