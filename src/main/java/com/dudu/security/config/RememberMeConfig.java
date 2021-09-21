package com.dudu.security.config;

import com.dudu.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * @Description:实现自动登陆功能配置类
 * @author:zhaomeng
 * @date:2021/9/21 10:12 上午
 */
//@Configuration
public class RememberMeConfig extends WebSecurityConfigurerAdapter {

    //注入数据源
    @Autowired
    private DataSource dataSource;
    @Autowired
    private MyUserDetailsService userDetailsService;

    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
        //设置数据源
        jdbcTokenRepository.setDataSource(dataSource);
        //自动执行创建表操作，第二次启动就要注释掉 否则会重复
//        jdbcTokenRepository.setCreateTableOnStartup(true);
        return  jdbcTokenRepository;
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
                .antMatchers("/test/auth").hasAnyRole("sale1","leader")
                //其他任何请求都要过验证
                .anyRequest().authenticated()
                //实现自动登陆功能
                // tokenRepository：操作数据库对象
                // tokenValiditySeconds：60 设置有效时长，单位是秒
                .and().rememberMe().tokenRepository(persistentTokenRepository()).tokenValiditySeconds(60)
                .userDetailsService(userDetailsService)
                //关闭csrf防护
                .and().csrf().disable();
    }

    @Bean
        //创建实现类，否则会报There is no PasswordEncoder mapped for the id "null"
    PasswordEncoder init(){
        return new BCryptPasswordEncoder();
    }

}
