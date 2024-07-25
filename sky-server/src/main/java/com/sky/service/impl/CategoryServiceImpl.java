package com.sky.service.impl;

import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.entity.Category;
import com.sky.mapper.CategoryMapper;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @Author: 程志琨
 * @Description: 分类管理服务层接口实现类
 * @Date: 2024/7/25 21:03
 * @Version: 1.0
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * @Author
     * @Date
     * @Description 新增分类
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void save(CategoryDTO categoryDTO) {
        Category category = new Category();
        //对象拷贝
        BeanUtils.copyProperties(categoryDTO, category);

        //新添加的分类状态默认为”禁用“status=0
        category.setStatus(StatusConstant.DISABLE);
        //设置创建时间和更新时间
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        //设置创建人id和修改人id
        category.setCreateUser(BaseContext.getCurrentId());
        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }
}
