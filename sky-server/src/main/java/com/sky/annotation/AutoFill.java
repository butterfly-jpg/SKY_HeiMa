package com.sky.annotation;

import com.sky.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author 
 * @Date  
 * @Description 自定义注解，用于标识某个方法需要进行功能字段自动填充处理
 * @Param 
 * @Return 
 * @Since version 1.0
 */

@Target(ElementType.METHOD)//指定注解的适用目标，ElementType.METHOD表示该注解用在方法声明上
@Retention(RetentionPolicy.RUNTIME)//指定注解的保留策略：RetentionPolicy.RUNTIME表示注解在运行时保留
public @interface AutoFill {

    //指定注解的属性和默认值。这里定义了一个枚举类型的属性value，该枚举类型value有两个属性值，分别为数据库操作类型UPDATE和INSERT
    OperationType value();
}
