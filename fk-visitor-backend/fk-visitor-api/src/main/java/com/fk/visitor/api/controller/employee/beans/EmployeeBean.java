package com.fk.visitor.api.controller.employee.beans;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class EmployeeBean {

    @ExcelProperty("姓名")
    private String name;

    @ExcelProperty("职务")
    private String department;
}
