package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Dish;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 程志琨
 * @Description:    菜单表 Mapper层接口
 * @Date: 2024/7/26 11:51
 * @Version: 1.0
 */

@Mapper
public interface DishMapper {

    /**
     * @Author
     * @Date
     * @Description 根据分类id查询菜品
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Select("select count(id) from dish where category_id = #{id}")
    Integer countByCategoryId(Long id);


    /**
     * @Author
     * @Date
     * @Description 新增菜品
     * AOP技术+自定义注解 解决公共字段自动填充功能
     * @Param Dish
     * @Return
     * @Since version 1.0
     */
    @AutoFill(OperationType.INSERT)
    void insert(Dish dish);

}
