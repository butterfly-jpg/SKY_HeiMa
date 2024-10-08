package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.PasswordConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.EmployeeDTO;
import com.sky.dto.EmployeeLoginDTO;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.exception.AccountLockedException;
import com.sky.exception.AccountNotFoundException;
import com.sky.exception.PasswordErrorException;
import com.sky.mapper.EmployeeMapper;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.EmployeeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    /**
     * 员工登录
     *
     * @param employeeLoginDTO
     * @return
     */
    public Employee login(EmployeeLoginDTO employeeLoginDTO) {
        String username = employeeLoginDTO.getUsername();
        String password = employeeLoginDTO.getPassword();

        //1、根据用户名查询数据库中的数据
        Employee employee = employeeMapper.getByUsername(username);

        //2、处理各种异常情况（用户名不存在、密码不对、账号被锁定）
        if (employee == null) {
            //账号不存在
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }

        //密码比对
        //密码进行md5加密，然后再进行比对
        password = DigestUtils.md5DigestAsHex(password.getBytes());
        if (!password.equals(employee.getPassword())) {
            //密码错误
            throw new PasswordErrorException(MessageConstant.PASSWORD_ERROR);
        }

        //status属性
        if (employee.getStatus() == StatusConstant.DISABLE) {
            //账号被锁定
            throw new AccountLockedException(MessageConstant.ACCOUNT_LOCKED);
        }

        //3、返回实体对象
        return employee;
    }

    /**
     * @Author
     * @Date
     * @Description 新增员工
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public void save(EmployeeDTO employeeDTO) {

        //employee中有employeeDTO中没有的属性
        //将employeeDTO拷贝到employee中，并把其他的属性进一步填写封装
        //对象属性拷贝
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置密码(MD5加密)和启用状态
        // 默认密码PasswordConstant.DEFAULT_PASSWORD=123456
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        //默认状态是启用中StatusConstant.ENABLE=1
        employee.setStatus(StatusConstant.ENABLE);


        /**
         * 通过AOP技术实现公共字段自动填充
         */
        //设置当前记录的创建时间和修改时间
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());

        //设置当前记录创建人Id和修改人Id
        //从ThreadLocal中得到id
//        employee.setCreateUser(BaseContext.getCurrentId());
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.insert(employee);
    }

    /**
     * @Author
     * @Date
     * @Description 员工分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public PageResult page(EmployeePageQueryDTO employeePageQueryDTO) {

        PageHelper.startPage(employeePageQueryDTO.getPage(), employeePageQueryDTO.getPageSize());
        Page<Employee> page = employeeMapper.pageQuery(employeePageQueryDTO);
        long total = page.getTotal();
        List<Employee> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * @Author
     * @Date
     * @Description 启用、禁用员工账号
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void status(Integer status, Long id) {
        Employee employee = Employee.builder()
                .status(status)
                .id(id)
                .build();
        employeeMapper.updateStatus(employee);
    }

    /**
     * @Author
     * @Date
     * @Description 根据id查询员工信息
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public Employee queryEmpById(Long id) {
        Employee employee = employeeMapper.getById(id);
        return employee;
    }

    /**
     * @Author
     * @Date
     * @Description 编辑员工信息
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void edit(EmployeeDTO employeeDTO) {
        //employee中有其他属性时employeeDTO中没有的
        //将employeeDTO拷贝到employee中，并把其他的属性进一步填写封装
        //对象属性拷贝
        Employee employee = new Employee();
        BeanUtils.copyProperties(employeeDTO, employee);

        //设置密码(MD5加密)和启用状态
        employee.setPassword(DigestUtils.md5DigestAsHex(PasswordConstant.DEFAULT_PASSWORD.getBytes()));
        employee.setStatus(StatusConstant.ENABLE);


        /**
         * 通过AOP技术实现公共字段自动填充
         */
        //设置当前记录修改时间
//        employee.setUpdateTime(LocalDateTime.now());

        //设置当前记录修改人Id
        //从ThreadLocal中得到id
//        employee.setUpdateUser(BaseContext.getCurrentId());

        employeeMapper.update(employee);

    }
}
