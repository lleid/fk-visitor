package com.fk.visitor.api.controller.employee;

import cn.kinkii.novice.framework.controller.BaseModelCRUDController;
import cn.kinkii.novice.framework.controller.BaseResult;
import cn.kinkii.novice.framework.controller.GlobalMessage;
import cn.kinkii.novice.framework.controller.exception.InternalServiceException;
import cn.kinkii.novice.framework.controller.exception.InvalidParamException;
import cn.kinkii.novice.framework.repository.ModelRepository;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.fk.visitor.api.controller.employee.beans.EmployeeBean;
import com.fk.visitor.lib.entity.Employee;
import com.fk.visitor.lib.repository.EmployeeRepository;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
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

    @RequestMapping(value = "/import", method = {RequestMethod.POST})
    public BaseResult importOperator(@RequestParam(value = "file") MultipartFile multipartFile) {
        ExcelReader reader = null;
        InputStream inputStream = null;
        if (multipartFile.isEmpty()) {
            throw new InvalidParamException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey()));
        }
        String prefix = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().lastIndexOf("."));
        if (!".xlsx".equals(prefix) && !".xls".equals(prefix)) {
            throw new InvalidParamException(getMessage(GlobalMessage.ERROR_PARAMETER.getMessageKey()));
        }

        try {
            employeeRepository.deleteAll();

            inputStream = multipartFile.getInputStream();
            reader = EasyExcel.read(inputStream, EmployeeBean.class, new EmployeeImportListener(employeeRepository)).build();
            ReadSheet sheet = EasyExcel.readSheet(0).build();
            reader.read(sheet);
        } catch (Exception e) {
            logger.error("read operator excel error: ", e.getMessage());
            throw new InternalServiceException(getMessage(GlobalMessage.ERROR_SERVICE.getMessageKey()));
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (Exception e) {
                    logger.error("close inputStream error: ", e.getMessage());
                }
            }
            if (reader != null) {
                reader.finish();
            }
        }

        return BaseResult.success(getMessage(GlobalMessage.SUCCESS.getMessageKey()));
    }
}
