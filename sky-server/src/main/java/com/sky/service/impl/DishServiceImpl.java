package com.sky.service.impl;

import com.sky.dto.DishDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    菜品Service层实现类
 * @Date: 2024/7/30 14:22
 * @Version: 1.0
 */

@Service
@Slf4j
public class DishServiceImpl implements DishService {

    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    /**
     * @Author
     * @Date
     * @Description 新增菜品
     *
     *      //既要向菜品表中插入数据，又要向口味表中插入数据
     *      保证功能的原子性，加入事务注解
     *
     * @Param dishDTO
     * @Return
     * @Since version 1.0
     */

    @Transactional
    @Override
    public void insertDishWithFlavors(DishDTO dishDTO) {
        //既要向菜品表中插入数据，又要向口味表中插入数据
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //菜品表只需要插入一条数据
        dishMapper.insert(dish);

        Long dishId = dish.getId();

        //口味表需要插入n条数据,用户可能会有n种口味,也有可能没有填写口味
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){

            //前端是没有把菜品的dishId传入到flavor对象的dishId的，需要我们自己增加菜品的时候回值赋值
            for (DishFlavor dishFlavor :flavors) {
                dishFlavor.setDishId(dishId);
            }

            //flavors是集合,通过sql批量插入语句插入到数据库表中
            dishFlavorMapper.insertBatch(flavors);
        }


    }
}
