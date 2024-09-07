package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: 程志琨
 * @Description:    用户Mapper层接口
 * @Date: 2024/9/7 20:12
 * @Version: 1.0
 */

@Mapper
public interface UserMapper {


    /**
     * @Author
     * @Date
     * @Description 插入新用户
     * @Param
     * @Return
     * @Since version 1.0
     */
    void insertUser(User user);

    /**
     * @Author
     * @Date
     * @Description 根据openid查询新用户
     * @Param
     * @Return
     * @Since version 1.0
     */

    User queryUserByOpenid(String openid);
}
