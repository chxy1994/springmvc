/*
author:chxy
data:2020/2/4
description:
*/
package com.bcd.springmvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @RequestMapping("/user/select.action")
    public @ResponseBody UserBean select(@RequestBody UserBean bean){

        bean.setName("hello");
        return bean;
    }

    @RequestMapping("/other/login.action")
    public String login(){

        System.out.println("hello");
        return "success";
    }
}


