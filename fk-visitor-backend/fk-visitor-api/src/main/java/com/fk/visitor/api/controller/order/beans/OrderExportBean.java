package com.fk.visitor.api.controller.order.beans;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

/**
 * @author lizz
 * @date 2020/1/15 0015 15:42
 */
@Data
public class OrderExportBean {

    @ColumnWidth(16)
    @ExcelProperty("拜访时间")
    private String visitAt;

    @ColumnWidth(16)
    @ExcelProperty("姓名")
    private String name;

    @ColumnWidth(16)
    @ExcelProperty("手机号")
    private String mobile;

    @ColumnWidth(16)
    @ExcelProperty("证件号")
    private String idCard;

    @ColumnWidth(12)
    @ExcelProperty("公司")
    private String company;

    @ColumnWidth(20)
    @ExcelProperty("职务")
    private String job;

    @ColumnWidth(15)
    @ExcelProperty("拜访事由")
    private String purpose;

    @ColumnWidth(15)
    @ExcelProperty("参观区域")
    private String visitArea;

    @ColumnWidth(15)
    @ExcelProperty("位置")
    private String station;

    @ColumnWidth(20)
    @ExcelProperty("签出时间")
    private String signOutAt;
}
