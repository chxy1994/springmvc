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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {

    @RequestMapping("/user/select.action")
    public @ResponseBody UserBean select(@RequestBody UserBean bean){

        bean.setName("hello");
        return bean;
    }

    @RequestMapping("/other/login.action")
    public ModelAndView login(HttpServletRequest request,HttpServletResponse response,
                              ModelAndView model) throws ServletException, IOException {

        model.setViewName( "forward:/user/login_success.action");
        return model;
    }

    @RequestMapping("/user/login_success.action")
    public  String loginSuccess(){

        return "success";
    }

    @RequestMapping("/other/login_failure.action")
    public  String loginFailure(){

        return "failure";
    }
}


