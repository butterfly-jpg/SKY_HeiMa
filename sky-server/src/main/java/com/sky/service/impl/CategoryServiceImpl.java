package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.context.BaseContext;
import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.CategoryMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealMapper setmealMapper;


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

        /**
         * 通过AOP技术实现公共字段自动填充
         */
        //设置创建时间和更新时间
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());
        //设置创建人id和修改人id
//        category.setCreateUser(BaseContext.getCurrentId());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.insert(category);
    }

    /**
     * @Author
     * @Date
     * @Description 分类分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public PageResult page(CategoryPageQueryDTO categoryPageQueryDTO) {

        PageHelper.startPage(categoryPageQueryDTO.getPage(), categoryPageQueryDTO.getPageSize());
        Page<Category> page = categoryMapper.pageQuery(categoryPageQueryDTO);
        long total = page.getTotal();
        List<Category> records = page.getResult();
        return new PageResult(total, records);
    }

    /**
     * @Author
     * @Date
     * @Description 根据id删除分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public void delete(Long id) {
        //判断当前分类是否关联了菜品或者套餐
        //1.判断该类型下是否关联菜品
        Integer dishId = dishMapper.countByCategoryId(id);
        if(dishId > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_DISH);
        }

        //2.判断该类型下是否关联套餐
        Integer categoryId = setmealMapper.countByCategoryId(id);
        if(categoryId > 0){
            throw new DeletionNotAllowedException(MessageConstant.CATEGORY_BE_RELATED_BY_SETMEAL);
        }

        //3不存在关联菜品或套餐，可以安心删除该分类
        categoryMapper.delete(id);
    }

    /**
     * @Author
     * @Date
     * @Description 修改分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public void edit(CategoryDTO categoryDTO) {
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);

        //修改启用禁用状态，默认为禁用status=0
        category.setStatus(StatusConstant.ENABLE);
        /**
         * 通过AOP技术实现公共字段自动填充
         */
        //设置修改时间和修改人id
//        category.setUpdateTime(LocalDateTime.now());
//        category.setUpdateUser(BaseContext.getCurrentId());

        categoryMapper.update(category);
    }


    /**
     * @Author
     * @Date
     * @Description 启用禁用分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public void openOrCloseStatus(Integer status, Long id) {
        categoryMapper.updateStatus(status, id);
    }

    /**
     * @Author
     * @Date
     * @Description 根据类型查询分类
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public List<Category> queryByType(Integer type) {
        List<Category> list = categoryMapper.queryByType(type);
        return list;
    }
}
