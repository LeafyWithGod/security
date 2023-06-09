package com.it.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.security.domain.User;
import org.springframework.stereotype.Repository;

@Repository //消除mapper爆红，不加也可，用@Component也可
public interface UserMapper extends BaseMapper<User> {
}