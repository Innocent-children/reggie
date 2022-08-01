package com.itcast.reggie.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 员工实体类
 */
@Data
public class Employee {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private String userName;
    private String password;
    private String sex;
    private String idNumber;  //身份证号码
    private Integer status;
    private String phone;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    @TableField(fill = FieldFill.INSERT)
    private Long createUser;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateUser;
}
