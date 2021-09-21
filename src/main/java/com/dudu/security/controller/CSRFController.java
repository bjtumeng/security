package com.dudu.security.controller;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Description:
 * @author:zhaomeng
 * @date:2021/9/21 4:22 下午
 */
public class CSRFController {

     //1.访问这个连接，跳转到登陆页面
    @GetMapping("/csrf")
    public String test(Model model){
        return "csrf/csrfTest";
    }
    //3.跳转到输出token网页
    @PostMapping("/update_token")
    public String getToken(){
        System.out.println("-----");
        return "csrf/csrf_token";
    }

    @GetMapping("/error")
    public String error(){
        return "error";
    }
    @GetMapping("/login")
    public String login(  Model model){
        return "login";
    }
}
