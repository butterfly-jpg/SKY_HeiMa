package com.sky.service;

import com.sky.dto.SetmealDTO;

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
}
