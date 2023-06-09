package com.it.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration
public class SecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    /**
     * 注意：
     * boot2.5版本需要该配置类继承WebSecurityConfigurerAdapter类并实现方法
     * boot2.7版本则只需要把passwordEncoder注册成一个bean就可以了
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * IgnoreUrlsConfig类为配置文件安全白名单集合
     *
     * @return
     */
    @Bean
    public IgnoreUrlsConfig ignoreUrlsConfig() {
        return new IgnoreUrlsConfig();
    }

    /**
     * 注意：
     * boot2.5版本需要该配置类继承WebSecurityConfigurerAdapter类实现方法，且需要在方法上面加上@bean来暴露给spring容器
     * boot2.7版本则只需要把自动注入AuthenticationManager并getAuthenticationManager注册成一个bean就可以了
     */
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
