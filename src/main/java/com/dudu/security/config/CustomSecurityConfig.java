package com.dudu.security.config;

import com.dudu.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @Description:实现UserDetailsService接口
 * @author:zhaomeng
 * @date:2021/9/18 6:23 下午
 */
//@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        //设置用到的UserDetailsService实现类
        auth.userDetailsService(userDetailsService).passwordEncoder(init());

    }

    @Override
    protected void configure(HttpSecurity security) throws Exception {
        //退出设置,logoutUrl("/logout")，不用写logout logoutSuccessUrl：退出之后跳转的url
        security.logout().logoutUrl("/logout").logoutSuccessUrl("/test/logout").permitAll();
        //没有权限跳转到的页面
        security.exceptionHandling().accessDeniedPage("/unauth.html");
       security.formLogin()
               //自定义用户登陆页面,需要认证的页面都会跳转到这个页面
               .loginPage("/login.html")
               //登陆页面表单提交到什么地址，这个地址不认证
               .loginProcessingUrl("/user/login")
               //登陆成功，跳转路径
               .defaultSuccessUrl("/sucess.html").permitAll()
               //设置哪些路径不需要认证可以直接访问
               .and().authorizeRequests().antMatchers("/","user/login").permitAll()
               //1.hasAuthority 只有具有role权限才能访问这个路径
//               .antMatchers("/test/auth").hasAnyAuthority("role")
               //2.hasAuthority 具有role权限或者manager访问这个路径
//               .antMatchers("/test/auth").hasAnyAuthority("role,manager")
               //3.hasRole  具有role权限或者manager访问这个路径  查看源码可知权限需要：ROLE_开头
//               .antMatchers("/test/auth").hasRole("sale")
               //4.hasAnyRole  具有role权限或者manager访问这个路径  查看源码可知权限需要：ROLE_开头
               .antMatchers("/test/auth").hasAnyRole("sale1","leader")
               //其他任何请求都要过验证
               .anyRequest().authenticated()
               //关闭csrf防护
               .and().csrf().disable();
    }

    @Bean
    //创建实现类，否则会报There is no PasswordEncoder mapped for the id "null"
    PasswordEncoder init(){
        return new BCryptPasswordEncoder();
    }
}
