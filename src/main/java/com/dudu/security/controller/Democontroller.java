package com.dudu.security.controller;

import com.dudu.security.entity.Users;
import com.dudu.security.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description:
 * @author:zhaomeng
 * @date:2021/9/17 8:11 下午
 */
@RestController
public class Democontroller {
    @Autowired
    private MyUserDetailsService service;

//   @RequestMapping("/sucess")
//    public String sucess(){
//       return "sucess";
//   }

    @RequestMapping("/user/login")
    public void login(Users users){
        service.loadUserByUsername(users.getUsername());
    }
    @RequestMapping("/test/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("/test/test")
    public String test1(){
        return "test";
    }
    @RequestMapping("/test/logout")
    public String logout(){
        return "logout";
    }
    @RequestMapping("/test/auth")
    public String auth(Users users){
        return "auth";
    }

    @RequestMapping("/update")
//    @Secured({"ROLE_sale","ROLE_manager"})
    //可以是hasAnyAuthority、hasAuthority、hasAnyRole、hasRole
    @PostAuthorize("hasAnyAuthority('admin')")
    public String update(Users users){
        System.out.println("update......");
        return "hello update";
    }

    @RequestMapping("/getALl")
    @PostAuthorize("hasAnyAuthority('admins')")
    @PostFilter("filterObject.username =='admin1'")
    public List<Users> getALl(){
        ArrayList<Users> list =new ArrayList<>();
        list.add(new Users(11,"admin1","666"));
        list.add(new Users(21,"admin2","888"));
        //返回结果 [{"id":11,"username":"admin1","password":"666"}]
        return list;
    }

    @RequestMapping("/getTestPreFilter")
    @PostAuthorize("hasAnyAuthority('admins')")
    //对传入参数进行过滤
    @PreFilter(value="filterObject.id%2 ==0")
    public List<Users> getTestPreFilter(@RequestBody List<Users> list){
       list.forEach(t->{
           System.out.println(t.getId() + "\t" + t.getUsername());
       });
       return list;
    }
}
