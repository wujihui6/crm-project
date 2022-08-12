package com.wu.crm.Settings.Service;

import com.wu.crm.Settings.domain.User;

import java.util.List;
import java.util.Map;

public interface userService {
    User queryUserByLoginActAndPwd(Map<String,Object> map);
    List<User> squeryAllUsers();
}
