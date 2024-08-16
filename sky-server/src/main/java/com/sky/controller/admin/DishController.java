package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    菜品Controller层相关接口
 * @Date: 2024/7/30 14:14
 * @Version: 1.0
 */

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "菜品相关接口")
@Slf4j
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    @ApiOperation("新增菜品")
    public Result insertDishWithFlavors(@RequestBody DishDTO dishDTO){
        log.info("新增菜品：{}", dishDTO);
        dishService.insertDishWithFlavors(dishDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("菜品分页查询")
    public Result<PageResult> dishPageQuery(DishPageQueryDTO dishPageQueryDTO){//请求参数类型是查询参数,放在DTO实体类中
        log.info("菜品分页查询:{}", dishPageQueryDTO);
        PageResult pageResults = dishService.dishPageQuery(dishPageQueryDTO);
        return Result.success(pageResults);
    }


    @DeleteMapping
    @ApiOperation("批量删除菜品")
    public Result batchDishDelete(@RequestParam List<Long> ids){//查询参数ids
        log.info("菜品批量删除：{}", ids);
        dishService.batchDishDelete(ids);
        return Result.success();
    }


    /**
     * @Author
     * @Date
     * @Description 修改菜品
     * 说明：
     * 根据产品原型分析接口设计：共需要设计4个接口
     * 接口1：根据菜品id查询菜品实现数据回显功能
     * 接口2：根据类型查询分类（已实现）
     * 接口3：文件上传（已实现）
     * 接口4：修改菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    /**
     * @Author
     * @Date
     * @Description 接口1：根据菜品id查询菜品实现数据回显功能
     * @Param
     * @Return
     * @Since version 1.0
     */
    @GetMapping("/{id}")
    @ApiOperation("根据id查询菜品")
    public Result<DishVO> queryDishWithFlavorsById(@PathVariable Long id){
        log.info("根据id查询菜品：{}", id);
        DishVO dishVO = dishService.queryDishById(id);
        return Result.success(dishVO);
    }
    /**
     * @Author
     * @Date
     * @Description 接口4：修改菜品
     * @Param
     * @Return
     * @Since version 1.0
     */
    @PutMapping
    @ApiOperation("修改菜品")
    public Result updateDishWithFlavors(@RequestBody DishDTO dishDTO){
        dishService.updateDish(dishDTO);
        return Result.success();
    }

    /**
     * @Author
     * @Date
     * @Description 菜品起售停售
     * @Param
     * @Return
     * @Since version 1.0
     */
    @PostMapping("/status/{status}")
    @ApiOperation("菜品起售停售")
    public Result startOrEndStatus(@PathVariable Integer status, @RequestParam Long id){//路径参数status和查询参数id
        dishService.startOrEndStatus(status, id);
        return Result.success();
    }

    @GetMapping("/list")
    @ApiOperation("根据分类id查询菜品")
    public Result<List<Dish>> queryDishByCategoryId(@RequestParam Long categoryId){
        List<Dish> list = dishService.queryDishByCategoryId(categoryId);
        return Result.success(list);
    }


}
