package com.sky.service;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.SetmealVO;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    套餐service层接口
 * @Date: 2024/8/16 20:54
 * @Version: 1.0
 */


public interface SetmealService {

    /**
     * @Author
     * @Date
     * @Description 新增套餐，setmealDishes套餐包含的菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    void insertSetmealWithSetmealDishes(SetmealDTO setmealDTO);

    /**
     * @Author
     * @Date
     * @Description 套餐分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    PageResult setmealPageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * @Author
     * @Date
     * @Description 批量删除套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    void deleteBatchSetmeal(List<Long> ids);

    /**
     * @Author
     * @Date
     * @Description 套餐起售停售
     * @Param
     * @Return
     * @Since version 1.0
     */

    void statusOpenOrClose(Long status, Long id);

    /**
     * @Author
     * @Date
     * @Description 编辑套餐:
     * 分两步：
     * 第一步：根据前端传入的id查询套餐数据 实现数据回显 功能
     * 第二部：修改套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    void updateSetmeal(SetmealDTO setmealDTO);

    /**
     * @Author
     * @Date
     * @Description 根据id查询套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    SetmealVO querySetmealById(Long id);
}
