package com.sky.aspect;

import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * @Author: 程志琨
 * @Description:    自定义切面类 AutoFillAspect 封装所有的通知方法
 * @Date: 2024/7/27 11:35
 * @Version: 1.0
 */

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    /**
     * @Author
     * @Date
     * @Description 切入点
     * 分析：execution(* com.sky.mapper.*.*(..))是第一个条件，表示切入点表达式，定义了要匹配的方法
     *      @annotation(com.sky.annotation.AutoFill) 是第二个条件，表示用来匹配使用了 @AutoFill 注解的方法
     * 综合来看：定义一个切入点，它将匹配 com.sky.mapper 包及其子包中所有类的所有方法，并且这些方法必须被 @AutoFill 注解所标记。
     *
     * 这里引入注解@Pointcut 实现重用切入点表达式：通知在同一个切面中使用(不知道重用除了增加代码量之外有什么优点，可能还没理解到位吧 >_<)
     *
     * @Param
     * @Return
     * @Since version 1.0
     */
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoFillPointCut(){}

    
    /**
     * @Author 
     * @Date
     * @Description 前置通知，在通知中进行公共字段的赋值。
     *
     * @Param 
     * @Return 
     * @Since version 1.0
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint){

        //后续加入功能实现，先调试
        log.info("开始进行公共字段自动填充...");
        //完善通知方法具体功能
        //由于对数据库操作类型的不同，对操作哪些公共字段就有了不同
        //1、因此要先获取当前被拦截的方法上的数据库操作类型是 update 还是 insert
        MethodSignature signature = (MethodSignature)joinPoint.getSignature();//获取方法签名类对象
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);//获取方法上@AutoFill注释的对象

        //2、获取当前被拦截的方法的参数即实体对象,这里是将方法的所有参数全部获取放到Object[]数组中
        Object[] args = joinPoint.getArgs();
        //约定数组的第一个元素是我们想要的实体类对象。同时由@AutoFill注释的所有方法的参数不一定都相同(不同功能方法有不同类型的参数)
        //所以用Object来修饰
        Object entity = args[0];

        //3、为步骤二获取到的实体对象参数准备赋值的数据
        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        //4、根据当前不同的操作类型，为对应的实体对象参数的属性进行赋值(反射机制)
        if(autoFill.value() == OperationType.INSERT){
            //插入操作，对4个公共字段都要进行赋值
            try {

                //反射机制获取entity的4个字段的set方法
                Method setCreateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                //反射机制为字段赋值
                setCreateTime.invoke(entity, now);
                setCreateUser.invoke(entity, currentId);
                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(autoFill.value() == OperationType.UPDATE){
            //修改操作，只需要对2个公共字段赋值即可updateTime和updateUser
            try {
                Method setUpdateTime = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

                setUpdateTime.invoke(entity, now);
                setUpdateUser.invoke(entity, currentId);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }


}
