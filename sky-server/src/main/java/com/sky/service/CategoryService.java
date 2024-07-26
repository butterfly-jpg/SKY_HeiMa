package com.sky.service;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;

import java.util.ArrayList;

/**
 * @Author: 程志琨
 * @Description: 分类管理服务层接口
 * @Date: 2024/7/25 21:02
 * @Version: 1.0
 */

public interface CategoryService {

    /**
     * @Author
     * @Date
     * @Description 新增分类
     * @Param
     * @Return
     * @Since version 1.0
     */

    void save(CategoryDTO categoryDTO);

    /**
     * @Author
     * @Date
     * @Description 分类分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    PageResult page(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * @Author
     * @Date
     * @Description 根据id删除分类
     * @Param
     * @Return
     * @Since version 1.0
     */

    void delete(Long id);

    /**
     * @Author
     * @Date
     * @Description 修改分类
     * @Param
     * @Return
     * @Since version 1.0
     */

    void edit(CategoryDTO categoryDTO);

    /**
     * @Author
     * @Date
     * @Description 启用禁用分类
     * @Param
     * @Return
     * @Since version 1.0
     */

    void openOrCloseStatus(Integer status, Long id);

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
