package com.sky.mapper;

import com.sky.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

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
    void insert(Category category);

}
