package com.dudu.security.config;

import com.dudu.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description:实现UserDetailsService接口
 * @author:zhaomeng
 * @date:2021/9/18 6:23 下午
 */
@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置用到的UserDetailsService实现类
        auth.userDetailsService(userDetailsService).passwordEncoder(init());
//        auth.userDetailsService(userDetailsService);

    }

    @Bean
    //创建实现类，否则会报There is no PasswordEncoder mapped for the id "null"
    PasswordEncoder init(){
        return new BCryptPasswordEncoder();
    }
}
