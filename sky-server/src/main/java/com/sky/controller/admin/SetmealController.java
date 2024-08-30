package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Info;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    套餐controller层
 * @Date: 2024/8/16 20:39
 * @Version: 1.0
 */

@RestController
@RequestMapping("/admin/setmeal")
@Api("套餐相关接口")
@Slf4j
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @PostMapping
    @ApiOperation("新增套餐")
    public Result insertSetmealWithSetmealDishes(@RequestBody SetmealDTO setmealDTO){
        log.info("新增套餐:{}", setmealDTO);
        setmealService.insertSetmealWithSetmealDishes(setmealDTO);
        return Result.success();
    }

    @GetMapping("/page")
    @ApiOperation("套餐分页查询")
    public Result<PageResult> setmealPageQuery(SetmealPageQueryDTO setmealPageQueryDTO){
        log.info("套餐分页查询：{}", setmealPageQueryDTO);
        PageResult pageResult = setmealService.setmealPageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    @DeleteMapping
    @ApiOperation("批量删除套餐")
    public Result deleteBatchSetmeal(@RequestParam List<Long> ids){
        log.info("批量删除套餐:{}", ids);
        setmealService.deleteBatchSetmeal(ids);
        return Result.success();
    }

    @PostMapping("/status/{status}")
    @ApiOperation("套餐起售停售")
    public Result statusOpenOrClose(@PathVariable Long status, @RequestParam Long id){
        log.info("套餐起售停售：{},{}", status, id);
        setmealService.statusOpenOrClose(status, id);
        return Result.success();
    }

    @PutMapping
    @ApiOperation("编辑套餐")
    public Result updateSetmeal(@RequestBody SetmealDTO setmealDTO){
        log.info("编辑修改套餐：{}", setmealDTO);
        setmealService.updateSetmeal(setmealDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询套餐")
    public Result<SetmealVO> querySetmealById(@PathVariable Long id){
        log.info("根据id查询套餐:{}", id);
        SetmealVO setmealVO = setmealService.querySetmealById(id);
        return Result.success(setmealVO);
    }

}
