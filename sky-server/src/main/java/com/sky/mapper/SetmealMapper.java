package com.sky.mapper;

import com.sky.annotation.AutoFill;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @Author: 程志琨
 * @Description:    套餐表 Mapper层接口
 * @Date: 2024/7/26 11:52
 * @Version: 1.0
 */

@Mapper
public interface SetmealMapper {

    /**
     * @Author
     * @Date
     * @Description 根据分类id查询套餐
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Select("select count(id) from setmeal where category_id = #{id}")
    Integer countByCategoryId(Long id);


    /**
     * @Author
     * @Date
     * @Description 新增套餐
     * @Param
     * @Return
     * @Since version 1.0
     */
    @AutoFill(OperationType.INSERT)
    void insert(Setmeal setmeal);
}
