import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.itcast.reggie.entity.Employee;
import com.itcast.reggie.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class Test {
    @Autowired
    private static EmployeeService employeeService1;

    public static void main(String[] args) {
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, "admin");
//        Employee emp1 = employeeService1.getOne(queryWrapper);
        ArrayList<Employee> empList = (ArrayList<Employee>) employeeService1.list();
        for (Employee emp :
                empList) {
            System.out.println(emp);
        }
    }
}
