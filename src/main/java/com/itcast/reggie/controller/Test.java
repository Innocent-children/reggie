package com.itcast.reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itcast.reggie.entity.Employee;
import com.itcast.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

public class Test {

    public void a() {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, "admin");
//        Employee emp1 = employeeService1.getOne(queryWrapper);
        EmployeeController employeeController = new EmployeeController();
//        ArrayList<Employee> empList = (ArrayList<Employee>) employeeController.employeeService.list(queryWrapper);
        System.out.println(employeeController.employeeService.getOne(queryWrapper));
//        for (Employee emp :
//                empList) {
//            System.out.println(emp);
//        }
    }

    public static void main(String[] args) {
        new Test().a();
    }
}
