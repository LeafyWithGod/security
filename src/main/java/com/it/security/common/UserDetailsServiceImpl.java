package com.it.security.common;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.it.security.domain.User;
import com.it.security.mapper.MenuMapper;
import com.it.security.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        LoginUser loginUser = getRedisUser(username);
        //返回
        return loginUser;
    }

    /**
     * 先去查找redis，redis没有再去查找mysql
     *
     * @param username
     * @return
     */
    public LoginUser getRedisUser(String username) {
        LoginUser cacheUser = redisCache.getCacheObject("login:" + username);
        if (!Objects.isNull(cacheUser)) {
            return cacheUser;
        }
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUserName, username);
        User user = userMapper.selectOne(queryWrapper);
        //校验
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名为空");
        }
        //查询权限信息
        List<String> list = menuMapper.selectPermsByUserId(user.getId());
        LoginUser loginUser = new LoginUser(user, list);
        //存入redis键值，60s*10十分钟过期
        redisCache.setCacheObject("login:" + username, loginUser, 60 * 10, TimeUnit.SECONDS);
        return loginUser;
    }
}
