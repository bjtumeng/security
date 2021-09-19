package com.dudu.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @author:zhaomeng
 * @date:2021/9/17 8:11 下午
 */
@RestController
public class Democontroller {

   @RequestMapping("/test")
    public String demo(){
       return "hello security";
   }
}
