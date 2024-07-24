package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;

public interface EmployeeService {

    /**
     * 员工登录
     * @param employeeLoginDTO
     * @return
     */
    Employee login(EmployeeLoginDTO employeeLoginDTO);

    /**
     * @Author
     * @Date
     * @Description 新增员工
     * @Param employeeDTO
     * @Return
     * @Since version 1.0
     */
    void save(EmployeeDTO employeeDTO);

    /**
     * @Author 
     * @Date  
     * @Description 员工分页查询
     * @Param 
     * @Return 
     * @Since version 1.0
     */
    PageResult page(EmployeePageQueryDTO employeePageQueryDTO);
    
}
