package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    菜品口味表Mapper接口
 * @Date: 2024/7/30 15:11
 * @Version: 1.0
 */

@Mapper
public interface DishFlavorMapper {

    /**
     * @Author
     * @Date
     * @Description 批量插入口味表
     * @Param
     * @Return
     * @Since version 1.0
     */

    void insertBatch(List<DishFlavor> flavors);

}
