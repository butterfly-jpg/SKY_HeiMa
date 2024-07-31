package com.sky.controller.admin;

import com.github.pagehelper.Page;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
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

}
