package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

/**
 * @Author: 程志琨
 * @Description:    菜品Service层接口
 * @Date: 2024/7/30 14:21
 * @Version: 1.0
 */


public interface DishService {

    /**
     * @Author
     * @Date
     * @Description 新增菜品
     * @Param DishDTO
     * @Return
     * @Since version 1.0
     */

    void insertDishWithFlavors(DishDTO dishDTO);


    /**
     * @Author
     * @Date
     * @Description 菜品分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO);

}
