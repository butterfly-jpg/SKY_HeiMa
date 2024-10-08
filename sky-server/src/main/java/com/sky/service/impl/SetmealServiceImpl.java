package com.sky.service.impl;

import com.github.pagehelper.Constant;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.entity.Setmeal;
import com.sky.entity.SetmealDish;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.SetmealDishMapper;
import com.sky.mapper.SetmealMapper;
import com.sky.result.PageResult;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
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
    }

    /**
     * @Author
     * @Date
     * @Description 套餐分页查询
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public PageResult setmealPageQuery(SetmealPageQueryDTO setmealPageQueryDTO) {
        PageHelper.startPage(setmealPageQueryDTO.getPage(), setmealPageQueryDTO.getPageSize());

        Page<Setmeal> page = setmealMapper.pageQuery(setmealPageQueryDTO);

        PageResult pageResult = new PageResult(page.getTotal(), page.getResult());

        return pageResult;
    }

    /**
     * @Author
     * @Date
     * @Description 批量删除套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void deleteBatchSetmeal(List<Long> ids) {
        //遍历集合ids
        for (Long id : ids){
            //根据id查询套餐
            Setmeal setmeal = setmealMapper.getSetmeal(id);
            //要求起售状态下的套餐不能删除
            if(setmeal.getStatus() == StatusConstant.DISABLE){
                throw new DeletionNotAllowedException(MessageConstant.SETMEAL_ON_SALE);
            }
            else {
                //删除数据时不仅删除setmeal表中的数据还要删除setmeal_dish表中的数据
                setmealMapper.deleteSetmeal(id);
                setmealDishMapper.deleteSetmealDish(id);
            }
        }

    }


    /**
     * @Author
     * @Date
     * @Description 套餐起售停售
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void statusOpenOrClose(Long status, Long id) {
        setmealMapper.changeStatus(status, id);
    }

    /**
     * @Author
     * @Date
     * @Description 编辑套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public void updateSetmeal(SetmealDTO setmealDTO) {
        //1.修改套餐表setmeal
        Setmeal setmeal = new Setmeal();
        BeanUtils.copyProperties(setmealDTO, setmeal);
        setmealMapper.updateSetmeal(setmeal);
        //2.删除套餐菜品的关联关系，对setmeal_dish表执行删除操作
        Long setmealId = setmealDTO.getId();
        setmealDishMapper.deleteSetmealDish(setmealId);
        //3.获取修改后的套餐菜品表关联数据,并把setmealId传入,因为发送端发送请求时SetmealDish集合中各元素的setmealId并未赋值
        //需要我们手动赋值
        List<SetmealDish> setmealDishes = setmealDTO.getSetmealDishes();
        for (SetmealDish setmealDish : setmealDishes){
            setmealDish.setSetmealId(setmealId);
        }
        //4.重新插入修改后的套餐和菜品之间的关联关系，对setmeal_dish表执行批量数据插入操作
        setmealDishMapper.insertBatch(setmealDishes);
    }

    /**
     * @Author
     * @Date
     * @Description 根据id查询套餐
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public SetmealVO querySetmealById(Long id) {
        Setmeal setmeal = setmealMapper.getSetmeal(id);
        List<SetmealDish> setmealDishes = setmealDishMapper.getSetmealWithDish(id);
        SetmealVO setmealVO = new SetmealVO();
        BeanUtils.copyProperties(setmeal, setmealVO);
        setmealVO.setSetmealDishes(setmealDishes);

        return setmealVO;
    }
}
