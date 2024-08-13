package com.sky.service;

import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.result.PageResult;
import com.sky.result.Result;

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

    /**
     * @Author
     * @Date
     * @Description 启用、禁用员工账号
     * @Param
     * @Return
     * @Since version 1.0
     */

    void status(Integer status, Long id);


    /**
     * @Author
     * @Date
     * @Description 根据id查询员工信息
     * @Param
     * @Return
     * @Since version 1.0
     */

    Employee queryEmpById(Long id);

    /**
     * @Author
     * @Date
     * @Description 编辑员工信息
     * @Param
     * @Return
     * @Since version 1.0
     */

    void edit(EmployeeDTO employeeDTO);
}
