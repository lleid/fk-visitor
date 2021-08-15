package com.fk.visitor.api.controller.mdata.employee.beans;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EmployeeBean {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("英文名")
    private String enName;

    @ExcelProperty("部门")
    private String department;
}
