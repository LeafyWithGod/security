package com.it.security.service.impl;

import com.it.security.common.LoginUser;
import com.it.security.common.RedisCache;
import com.it.security.common.ResponseResult;
import com.it.security.domain.User;
import com.it.security.mapper.UserMapper;
import com.it.security.service.LoginService;
import com.it.security.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        LoginUser principal = (LoginUser) authenticate.getPrincipal();
        String userName = principal.getUser().getUserName();
        String jwt = JwtUtil.createJWT(userName);

        return new ResponseResult(200, "登录成功", jwt);
    }

    @Override
    public ResponseResult logout() {
        //获取SecurityContextHolder中的用户name
        UsernamePasswordAuthenticationToken authentication =
                (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        LoginUser principal = (LoginUser) authentication.getPrincipal();
        String userName = principal.getUser().getUserName();
        //从redis中删除
        redisCache.deleteObject("login:" + userName);
        return new ResponseResult(200, "注销成功");
    }
}
