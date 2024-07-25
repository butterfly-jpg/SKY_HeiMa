package com.sky.controller.admin;

import com.sky.dto.CategoryDTO;
import com.sky.result.Result;
import com.sky.service.CategoryService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: 程志琨
 * @Description: 分类管理
 * @Date: 2024/7/25 20:55
 * @Version: 1.0
 */

@RestController
@RequestMapping("/admin/category")
@Slf4j
public class categoryController {
    
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
    
    
}
