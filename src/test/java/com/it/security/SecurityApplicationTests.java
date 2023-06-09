package com.it.security;

import com.it.security.domain.User;
import com.it.security.mapper.MenuMapper;
import com.it.security.mapper.UserMapper;
import com.it.security.utils.JwtUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootTest
class SecurityApplicationTests {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserMapper userMapper;
    @Value("${secure.login}")
    private String login;
    @Autowired
    private MenuMapper mapper;

    @Test
    public void testUserMapper() {
        List<User> users = userMapper.selectList(null);
        System.out.println(users);
    }

    @Test
    public void BCryptPasswordEncoder() {

//        $2a$10$1CwlfhPGtVrlgFnYnvxsKuFOKpsyZrTw/9qucUDOKgzfccKw8rgi.
//        $2a$10$RYdbqnIO/Tqw1Hlv/cexZ.CIZOOvHj1miTcGSppbnkkcH25v8/c6K

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode = bCryptPasswordEncoder.encode("1234");
        String encode1 = bCryptPasswordEncoder.encode("1234");
//        System.out.println(encode);
//        System.out.println(encode1);
        boolean matches = bCryptPasswordEncoder.
                matches("1234"
                        , "$2a$10$1CwlfhPGtVrlgFnYnvxsKuFOKpsyZrTw/9qucUDOKgzfccKw8rgi.");
        System.out.println(matches);

    }

    @Test
    public void JWT_TTL() {

        System.out.println(jwtUtil.JWT_TTL);

    }

    @Test
    public void login() {
        System.out.println(login);
    }

    @Test
    public void MapperTest() {
        List<String> list = mapper.selectPermsByUserId(2L);
        System.out.println(list);
    }

}
