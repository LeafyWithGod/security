package com.it.security.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.security.domain.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuMapper extends BaseMapper<Menu> {
    List<String> selectPermsByUserId(Long id);
}
