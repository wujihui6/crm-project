package com.wu.crm.Settings.Service.impl;

import com.wu.crm.Settings.Service.userService;
import com.wu.crm.Settings.domain.User;
import com.wu.crm.Settings.mapper.UserMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service(value = "userService")
public class userServiceImpl implements userService {
    @Resource
    private UserMapper userMapper;

    @Override
    public User queryUserByLoginActAndPwd(Map<String, Object> map) {
       return userMapper.selectUserByLoginActAndPwd(map);
    }

    @Override
    public List<User> squeryAllUsers() {
        return userMapper.selectAllUsers();
    }
}
