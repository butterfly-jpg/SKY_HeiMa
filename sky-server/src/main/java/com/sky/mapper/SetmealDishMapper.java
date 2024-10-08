package com.sky.mapper;

import com.sky.entity.SetmealDish;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    套餐菜品关联表Mapper接口
 * @Date: 2024/7/31 11:30
 * @Version: 1.0
 */

@Mapper
public interface SetmealDishMapper {

    /**
     * @Author
     * @Date
     * @Description 在setmeal_dish套餐菜品关联表中 通过dishId 查 setmealId是否存在(这里使用批量查询)
     * @Param
     * @Return
     * @Since version 1.0
     */

    List<Long> getSetmealIdsByDishIds(List<Long> dishIds);

    /**
     * @Author
     * @Date
     * @Description 套餐菜品表setmeal_dish中批量插入数据
     * @Param
     * @Return
     * @Since version 1.0
     */
    void insertBatch(List<SetmealDish> setmealDishes);

    /**
     * @Author
     * @Date
     * @Description 根据setmeal_id删除套餐菜品关联表
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Delete("delete from setmeal_dish where setmeal_id = #{setmealId}")
    void deleteSetmealDish(Long setmealId);

    /**
     * @Author
     * @Date
     * @Description 根据setmeal_id查询setmeal_dish表
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Select("select * from setmeal_dish where setmeal_id = #{id}")
    List<SetmealDish> getSetmealWithDish(Long id);
}
