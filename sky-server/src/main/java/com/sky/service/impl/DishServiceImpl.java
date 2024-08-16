package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
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
    @Autowired
    private SetmealDishMapper setmealDishMapper;

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

    /**
     * @Author
     * @Date
     * @Description 菜品分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public PageResult dishPageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.dishPageQuery(dishPageQueryDTO);
        long total = page.getTotal();
        List<DishVO> result = page.getResult();
        return new PageResult(total, result);
    }


    /**
     * @Author
     * @Date
     * @Description 批量删除菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    @Transactional
    public void batchDishDelete(List<Long> ids) {
        //判断菜品的起售状态
        for (Long id : ids) {
            //在菜品表中 通过菜品id 得到 菜品实体类数据
            Dish dish = dishMapper.getDishById(id);
            //起售状态下表示不能删除
            if(dish.getStatus() == StatusConstant.ENABLE){
                //欲删除起售状态的菜品数据 报MessageConstant.DISH_ON_SALE = "起售中的菜品不能删除";异常
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }

        //判断菜品是否被套餐关联
        //在setmeal_dish套餐菜品关联表中 通过dishId 查 setmealId是否存在(这里使用批量查询)
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishIds(ids);
        if(setmealIds != null && setmealIds.size() > 0){
            //欲删除有关联状态的菜品数据 报MessageConstant.DISH_BE_RELATED_BY_SETMEAL="当前菜品关联了套餐,不能删除";异常
            throw new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }

        //删除
        for (Long id : ids) {
            //删除菜品数据
            dishMapper.deleteById(id);
            //删除菜品数据关联的口味数据
            dishFlavorMapper.deleteByDishId(id);
        }
    }

    /**
     * @Author
     * @Date
     * @Description 根据id查询菜品
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Override
    public DishVO queryDishById(Long id) {
        DishVO dishVO = new DishVO();
        //根据菜品id查询菜品数据
        Dish dish = dishMapper.getDishById(id);
        //拷贝菜品数据dish实体类到dishVO实体类(dishVO专用于返回到前端的数据实体类)
        BeanUtils.copyProperties(dish, dishVO);
        //根据菜品id查询dish_flavor表的口味属性
        List<DishFlavor> dishFlavors = dishFlavorMapper.queryFlavorsByDishId(id);
        //为dishVO的口味属性赋值
        dishVO.setFlavors(dishFlavors);

        return dishVO;
    }

    /**
     * @Author
     * @Date
     * @Description 修改菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void updateDish(DishDTO dishDTO) {
        //创建Dish菜品实体类
        Dish dish = new Dish();
        //拷贝
        BeanUtils.copyProperties(dishDTO, dish);
        //更新
        dishMapper.update(dish);

        //口味数据如何修改 思路是：先删除再插入
        //删除原有的口味数据
        Long dishId = dishDTO.getId();
        dishFlavorMapper.deleteByDishId(dishId);

        //重新插入该dishId对应下的Flavors数据
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors != null && flavors.size() > 0){
            for (DishFlavor dishFlavor : flavors) {
                dishFlavor.setDishId(dishId);
            }
            dishFlavorMapper.insertBatch(flavors);
        }
    }


    /**
     * @Author
     * @Date
     * @Description 菜品起售停售
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void startOrEndStatus(Integer status, Long id) {
        Dish dish = Dish.builder()
                .status(status)
                .id(id)
                .build();

        dishMapper.updateStatus(dish);

    }

    /**
     * @Author
     * @Date
     * @Description 根据分类id查询菜品
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public List<Dish> queryDishByCategoryId(Long categoryId) {
        Dish dish = Dish.builder()
                .categoryId(categoryId)
                .status(StatusConstant.ENABLE)
                .build();
        return dishMapper.query(dish);
    }


}
