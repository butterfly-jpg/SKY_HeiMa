package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFill;
import com.sky.entity.Category;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.util.ArrayList;

/**
 * @Author: 程志琨
 * @Description:  分类管理Mapper层接口
 * @Date: 2024/7/25 21:17
 * @Version: 1.0
 */

@Mapper
public interface CategoryMapper {

    /**
     * @Author
     * @Date
     * @Description 新增分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Insert("insert into category (type, name, sort, status, create_time, update_time, create_user, update_user)" +
            "values" +
            "(#{type}, #{name}, #{sort}, #{status}, #{createTime}, #{updateTime}, #{createUser}, #{updateUser})")
    @AutoFill(value = OperationType.INSERT)
    void insert(Category category);

    /**
     * @Author
     * @Date
     * @Description 分类分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    Page<Category> pageQuery();

    /**
     * @Author
     * @Date
     * @Description 根据id删除分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Delete("delete from category where id = #{id}")
    void delete(Long id);

    /**
     * @Author
     * @Date
     * @Description 修改分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @AutoFill(OperationType.UPDATE)
    void update(Category category);

    /**
     * @Author
     * @Date
     * @Description 启用禁用分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Update("update category set status = #{status} where id = #{id}")
    void updateStatus(Integer status, Long id);

    /**
     * @Author
     * @Date
     * @Description 根据类型查询分类
     * @Param
     * @Return
     * @Since version 1.0
     */

    ArrayList<Category> queryByType(Integer type);

}
