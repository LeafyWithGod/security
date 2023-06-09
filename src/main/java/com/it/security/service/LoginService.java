package com.it.security.service;


import com.it.security.common.ResponseResult;
import com.it.security.domain.User;

public interface LoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
