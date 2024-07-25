package com.sky.controller.admin;

import com.sky.constant.JwtClaimsConstant;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.properties.JwtProperties;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import com.sky.utils.JwtUtil;
import com.sky.vo.EmployeeLoginVO;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 员工管理
 */
@RestController
@RequestMapping("/admin/employee")
@Slf4j
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 登录
     *
     * @param employeeLoginDTO
     * @return
     */
    @PostMapping("/login")
    public Result<EmployeeLoginVO> login(@RequestBody EmployeeLoginDTO employeeLoginDTO) {
        log.info("员工登录：{}", employeeLoginDTO);

        Employee employee = employeeService.login(employeeLoginDTO);

        //登录成功后，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID, employee.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getAdminSecretKey(),
                jwtProperties.getAdminTtl(),
                claims);

        EmployeeLoginVO employeeLoginVO = EmployeeLoginVO.builder()
                .id(employee.getId())
                .userName(employee.getUsername())
                .name(employee.getName())
                .token(token)
                .build();

        return Result.success(employeeLoginVO);
    }

    /**
     * 退出
     *
     * @return
     */
    @PostMapping("/logout")
    public Result<String> logout() {
        return Result.success();
    }

    /**
     * @Author
     * @Date
     * @Description 新增员工
     * @Param
     * @Return
     * @Since version 1.0
     */
    @PostMapping
    @ApiOperation("新增员工")
    public Result save(@RequestBody EmployeeDTO employeeDTO){
        log.info("新增员工：{}",employeeDTO);
        employeeService.save(employeeDTO);
        return Result.success();

    }

    /**
     * @Author
     * @Date
     * @Description 员工分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */
    @GetMapping("/page")
    @ApiOperation("员工分页查询")
    public Result<PageResult> page(EmployeePageQueryDTO employeePageQueryDTO){
        PageResult pageResult = employeeService.page(employeePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Author
     * @Date
     * @Description 启用、禁用员工账号
     * @Param
     * @Return
     * @Since version 1.0
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用员工账号")
    public Result status(@PathVariable Integer status, Long id){
        //status是路径参数，主要用于获取数据库的用户信息或特定内容
        //id是查询参数，主要用于查询、搜索、筛选用户或商品等额外的请求参数
        log.info("启用禁用员工账号：{},{}",status, id);
        employeeService.status(status, id);
        return Result.success();
    }

    /**
     * @Author
     * @Date
     * @Description
     * 编辑员工信息
     * 分为两步骤：
     * 步骤1： 根据id查询员工信息 实现 回显员工信息功能
     * 步骤2： 编辑员工信息 实现 修改员工信息功能
     * @Param
     * @Return
     * @Since version 1.0
     */
    /**
     * @Author
     * @Date
     * @Description 步骤1： 根据id查询员工信息 实现 回显员工信息功能
     * @Param 路径参数 Long id
     * @Return Employee对象
     * @Since version 1.0
     */

    @GetMapping("/{id}")//id是路径参数
    @ApiOperation("根据id查询员工信息")
    public Result<Employee> queryEmpById(@PathVariable Long id){
        Employee employee = employeeService.queryEmpById(id);
        return Result.success(employee);
    }

    /**
     * @Author
     * @Date
     * @Description 步骤2： 编辑员工信息 实现 修改员工信息功能
     * 注：登录账号是admin管理员，因此员工信息的启用状态不管是0还是1，admin账户都可以进行修改操作
     * @Param EmployeeDTO对象
     * @Return Result.success()
     * @Since version 1.0
     */
    @PutMapping
    @ApiOperation("编辑员工信息")
    public Result edit(@RequestBody EmployeeDTO employeeDTO){
        log.info("编辑员工信息：{}", employeeDTO);
        employeeService.edit(employeeDTO);
        return Result.success();
    }



}
