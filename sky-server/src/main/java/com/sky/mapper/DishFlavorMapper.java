package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    菜品口味表Mapper接口
 * @Date: 2024/7/30 15:11
 * @Version: 1.0
 */

@Mapper
public interface DishFlavorMapper {

    /**
     * @Author
     * @Date
     * @Description 批量插入口味表
     * @Param
     * @Return
     * @Since version 1.0
     */

    void insertBatch(List<DishFlavor> flavors);

    /**
     * @Author
     * @Date
     * @Description 删除菜品数据关联的口味数据
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Delete("delete from dish_flavor where dish_id = #{id}")
    void deleteByDishId(Long id);

    /**
     * @Author
     * @Date
     * @Description 根据菜品id查询口味
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Select("select * from dish_flavor where dish_id = #{id}")
    List<DishFlavor> queryFlavorsByDishId(Long id);

}
