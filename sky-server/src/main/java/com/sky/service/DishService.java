package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

import java.util.List;

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

    /**
     * @Author
     * @Date
     * @Description 批量删除菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    void batchDishDelete(List<Long> ids);

    /**
     * @Author
     * @Date
     * @Description 根据id查询菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    DishVO queryDishById(Long id);

    /**
     * @Author
     * @Date
     * @Description 修改菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    void updateDish(DishDTO dishDTO);
}
