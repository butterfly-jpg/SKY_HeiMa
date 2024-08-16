package com.sky.service.impl;

import com.sky.dto.SetmealDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 程志琨
 * @Description:    套餐Service层实现类
 * @Date: 2024/8/16 20:55
 * @Version: 1.0
 */

@Service
@Slf4j
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealMapper setmealMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    /**
     * @Author
     * @Date
     * @Description 新增套餐，setmealDishes套餐包含的菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void insertSetmealWithSetmealDishes(SetmealDTO setmealDTO) {
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);

        //套餐表只需插入一条数据即可
        setmealMapper.insert(setmeal);


        //获取setmealId
        Long setmealId = setmeal.getId();
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        if(setmealDishes != null && setmealDishes.size() > 0){
            for (SetmealDish setmealDish : setmealDishes) {
                setmealDish.setSetmealId(setmealId);
            }
            //批量插入
            setmealDishMapper.insertBatch(setmealDishes);
        }
        //套餐菜品表setmeal_dish中需要插入n条数据,一个套餐下可能会关联多个菜品


    }
}
