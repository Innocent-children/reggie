package com.itcast.reggie.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.itcast.reggie.entity.Employee;
import com.itcast.reggie.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

/**
 * 公共字段自动填充方法
 * 字段上的@TableField注解
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("公共字段自动填充[insert]...");
        metaObject.setValue("createUser", BaseContext.getCurrentId());
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        metaObject.setValue("updateTime", LocalDateTime.now());
        log.info(metaObject.toString());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("公共字段自动填充[update]...");
        metaObject.setValue("updateUser", BaseContext.getCurrentId());
        metaObject.setValue("updateTime", LocalDateTime.now());
        log.info(metaObject.toString());
    }
}
