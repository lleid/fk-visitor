package com.fk.visitor.lib.entity.system;

import cn.kinkii.novice.framework.entity.PkUUID;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fk.visitor.lib.entity.enums.Gender;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@ApiModel(value = "账号", description = "账号")
@Entity
@Table(name = "t_operator")
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class Operator extends PkUUID {

    @Column(name = "username", nullable = false, unique = true, length = 32)
    @ApiModelProperty(value = "用户名")
    private String username;

    @Column(name = "password", nullable = false)
    @ApiModelProperty(value = "密码")
    @JsonIgnore
    private String password;

    @Transient
    @ApiModelProperty(value = "密码原文（提交用）")
    private String plainPassword;

    @Column(name = "name", length = 32)
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "gender", length = 8)
    @ApiModelProperty(value = "性别")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    public String getGenderValue() {
        if (gender != null) {
            return gender.name();
        }
        return null;
    }

    @Column(name = "birthday", length = 16)
    @ApiModelProperty(value = "生日")
    private String birthday;

    @Column(name = "mobile", length = 16, unique = true)
    @ApiModelProperty(value = "手机")
    private String mobile;

    @Column(name = "email", length = 64, unique = true)
    @ApiModelProperty(value = "电子邮箱")
    private String email;

    @Column(name = "avatar", length = 128)
    @ApiModelProperty(value = "头像")
    private String avatar;

    @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinTable(name = "t_operator_role_map", joinColumns = {@JoinColumn(name = "operator_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    @ApiModelProperty(value = "用户角色")
    private List<OperatorRole> roles;

    @ApiModelProperty(value = "最后登录时间")
    @Column(name = "last_login_at")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date lastLoginAt = new Date();

    public Long getLastLoginAtByMs() {
        return lastLoginAt != null ? lastLoginAt.getTime() : null;
    }

    @ApiModelProperty(value = "创建时间")
    @Column(name = "create_at", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createAt = new Date();

    public Long getCreateAtByMs() {
        return createAt != null ? createAt.getTime() : null;
    }

    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "station_id")
    @ApiModelProperty("位置")
    private Station station;
}
