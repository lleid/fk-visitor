package com.fk.visitor.lib.entity.mdata;

import cn.kinkii.novice.framework.entity.PkNativeID;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@ApiModel(value = "员工管理", description = "员工管理")
@Entity
@Table(name = "t_employee")
@Getter
@Setter
@NoArgsConstructor
public class Employee extends PkNativeID {

    @ApiModelProperty(value = "姓名")
    @Column(name = "name ", length = 32)
    private String name;

    @ApiModelProperty(value = "英文姓名")
    @Column(name = "en_name ", length = 32)
    private String enName;

    @ApiModelProperty(value = "部门")
    @Column(name = "department ", length = 32)
    private String department;
}
