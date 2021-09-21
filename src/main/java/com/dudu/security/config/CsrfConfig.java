package com.dudu.security.config;

import com.dudu.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description: 针对于Csrf的配置类
 * @author:zhaomeng
 * @date:2021/9/21 4:19 下午
 */
@Configuration
public class CsrfConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置用到的UserDetailsService实现类
        auth.userDetailsService(userDetailsService).passwordEncoder(init());

    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        //没有权限跳转到的页面
        security.exceptionHandling().accessDeniedPage("/unauth.html");
        security.formLogin()
                //自定义用户登陆页面,需要认证的页面都会跳转到这个页面
                .loginPage("/login")
                .usernameParameter("username").passwordParameter("password");
//                .failureForwardUrl("/error");
                //开启csrf防护
//                .and().csrf().disable();
        security.authorizeRequests().antMatchers("/**update**").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
        //创建实现类，否则会报There is no PasswordEncoder mapped for the id "null"
    PasswordEncoder init(){
        return new BCryptPasswordEncoder();
    }
}
