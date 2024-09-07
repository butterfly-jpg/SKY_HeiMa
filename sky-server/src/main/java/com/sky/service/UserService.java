package com.sky.service;

import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;

/**
 * @Author: 程志琨
 * @Description:    用户Service层
 * @Date: 2024/9/7 19:48
 * @Version: 1.0
 */


public interface UserService {

    /**
     * @Author
     * @Date
     * @Description 微信用户登录
     * @Param
     * @Return
     * @Since version 1.0
     */

    User wxlogin(UserLoginDTO userLoginDTO);
}
