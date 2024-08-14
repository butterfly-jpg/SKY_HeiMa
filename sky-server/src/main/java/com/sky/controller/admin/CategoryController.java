package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * @Author: 程志琨
 * @Description: 分类管理
 * @Date: 2024/7/25 20:55
 * @Version: 1.0
 */

@RestController
@RequestMapping("/admin/category")
@Slf4j
@Api(tags = "分类相关接口")
public class CategoryController {
    
    @Autowired
    private CategoryService categoryService;
    
    
    /**
     * @Author 
     * @Date  
     * @Description 新增分类
     * @Param CategoryDTO
     * @Return Result.success()
     * @Since version 1.0
     */
    @PostMapping
    @ApiOperation("新增分类")
    public Result save(@RequestBody CategoryDTO categoryDTO){
        categoryService.save(categoryDTO);
        return Result.success();
    }


    /**
     * @Author
     * @Date
     * @Description 分类分页查询
     * @Param 查询参数 CategoryPageQueryDTO
     * @Return  Result.success(PageResult)
     * @Since version 1.0
     */
    @GetMapping("/page")
    @ApiOperation("分类分页查询")
    public Result<PageResult> page(CategoryPageQueryDTO categoryPageQueryDTO){
        PageResult pageResult = categoryService.page(categoryPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * @Author
     * @Date
     * @Description 根据id删除分类
     * 注：当分类关联了菜品或者套餐时，此分类不允许删除。
     * 因此要查 菜单表dish 的 category_id字段 和 套餐表setmeal category_id字段  是否为0
     * 如果都是0那么可以安心删除该分类
     * 只要有一个不是0那么就不可以删除
     * @Param 查询参数Long id
     * @Return  Result.success()
     * @Since version 1.0
     */
    @DeleteMapping
    @ApiOperation("根据id删除分类")
    public Result delete(Long id){
        categoryService.delete(id);
        return Result.success();
    }



    /**
     * @Author
     * @Date
     * @Description 修改分类 分为两步骤
     * 步骤一：根据id查询分类信息 实现 数据回显功能
     * 注：在苍穹外卖中，我发现页面的分类管理中修改功能已经实现了数据回显功能，所以不在考虑该功能实现
     * 步骤二：编辑分类信息 实现 修改数据功能
     * @Param
     * @Return
     * @Since version 1.0
     */
    @PutMapping
    @ApiOperation("修改分类")
    public Result edit(@RequestBody CategoryDTO categoryDTO){
        categoryService.edit(categoryDTO);
        return Result.success();
    }

    /**
     * @Author
     * @Date
     * @Description 启用禁用分类
     * @Param   路径参数status 查询参数id
     * @Return
     * @Since version 1.0
     */
    @PostMapping("/status/{status}")
    @ApiOperation("启用禁用分类")
    public Result status(@PathVariable Integer status, Long id){
        categoryService.openOrCloseStatus(status, id);
        return Result.success();
    }


    /**
     * @Author
     * @Date
     * @Description 根据类型查询分类
     * @Param   查询参数type
     * @Return  Result.success(ArrayList)
     * @Since version 1.0
     */
    @GetMapping("/list")
    @ApiOperation("根据类型查询分类")
    public Result<ArrayList> queryByType(Integer type){
        ArrayList<Category> arrayList = categoryService.queryByType(type);
        return Result.success(arrayList);
    }


}
