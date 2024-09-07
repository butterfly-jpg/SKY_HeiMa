package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 程志琨
 * @Description:    用户Service实现类
 * @Date: 2024/9/7 19:48
 * @Version: 1.0
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    //微信服务接口地址
    private static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;


    /**
     * @Author
     * @Date
     * @Description 微信用户登录
     * @Param
     * @Return
     * @Since version 1.0
     */

    @Override
    public User wxlogin(UserLoginDTO userLoginDTO) {
        //得到openid
        String openid = getOpenId(userLoginDTO.getCode());

        //判断openid是否为空,为空代表登陆失败
        if(openid == null){
            throw new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        //判断当前用户是否为新用户
        User user = userMapper.queryUserByOpenid(openid);
        //如果是新用户，则插入到数据库中
        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            //新用户插入数据库
            userMapper.insertUser(user);
        }
        //不是新用户直接返回
        return user;
    }

    /**
     * @Author
     * @Date
     * @Description 向微信服务器发送请求得到openid
     * 发送参数有四个：
     * 1、appid
     * 2、secret
     * 3、js_code
     * 4、grant_type
     * @Param
     * @Return
     * @Since version 1.0
     */

    private String getOpenId(String code) {

        //设置请求参数
        Map<String, String> params = new HashMap<>();
        params.put("appid", weChatProperties.getAppid());
        params.put("secret", weChatProperties.getSecret());
        params.put("js_code", code);
        params.put("grant_type", "authorization_code");
        //向微信服务端发送请求
        String json = HttpClientUtil.doGet(WX_LOGIN, params);

        JSONObject jsonObject = JSON.parseObject(json);
        String openid = jsonObject.getString("openid");
        return openid;
    }
}
