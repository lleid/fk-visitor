package com.fk.visitor.api.controller.mdata.employee;

import cn.kinkii.novice.framework.utils.KBeanUtils;
import cn.kinkii.novice.security.context.KAuthenticatingContext;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.fk.visitor.api.controller.mdata.employee.beans.EmployeeBean;
import com.fk.visitor.lib.entity.mdata.Employee;
import com.fk.visitor.lib.repository.mdata.EmployeeRepository;
import com.google.common.collect.Lists;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Slf4j
@Data
public class EmployeeImportListener extends AnalysisEventListener<EmployeeBean> {

    private static final Integer BATCH_COUNT = 1000;

    private EmployeeRepository employeeRepository;

    private KAuthenticatingContext kAuthenticatingContext;

    private List<EmployeeBean> employeeList = Lists.newArrayList();

    public EmployeeImportListener(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public void invoke(EmployeeBean employeeBean, AnalysisContext analysisContext) {
        employeeList.add(employeeBean);
        if (employeeList.size() > BATCH_COUNT) {
            saveemployee();
            employeeList.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        saveemployee();
    }

    public void saveemployee() {
        if (employeeList.size() > 0) {
            employeeList.forEach(bean -> {
                if (bean != null) {
                    Employee employee = new Employee();
                    KBeanUtils.copyPropertiesIgnoreNull(bean, employee);
                    if (StringUtils.isNotEmpty(bean.getName())) {
                        employeeRepository.save(employee);
                    }
                }
            });
        }
    }
}
