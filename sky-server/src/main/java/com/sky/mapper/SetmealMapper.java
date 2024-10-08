package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

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

    /**
     * @Author
     * @Date
     * @Description 套餐分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    Page<Setmeal> pageQuery(SetmealPageQueryDTO setmealPageQueryDTO);

    /**
     * @Author
     * @Date
     * @Description 根据id查询套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Select("select * from setmeal where id = #{id}")
    Setmeal getSetmeal(Long id);

    /**
     * @Author
     * @Date
     * @Description 根据id删除套餐
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Delete("delete from setmeal where id = #{id}")
    void deleteSetmeal(Long id);

    /**
     * @Author
     * @Date
     * @Description 套餐起售停售
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Update("update setmeal set status = #{status} where id = #{id}")
    void changeStatus(Long status, Long id);

    /**
     * @Author
     * @Date
     * @Description 修改套餐
     * @Param
     * @Return
     * @Since version 1.0
     */
    @AutoFill(OperationType.UPDATE)
    void updateSetmeal(Setmeal setmeal);





}
