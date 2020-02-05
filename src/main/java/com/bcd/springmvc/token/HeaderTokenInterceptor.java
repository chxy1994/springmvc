/*
author:chxy
data:2020/2/4
description:
*/
package com.bcd.springmvc.token;


import org.bouncycastle.math.raw.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ThinkPad on 2017/6/20.
 */
public class HeaderTokenInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(HeaderTokenInterceptor.class);
    JwtUtil jwtUtil = new JwtUtil();
    private static Map<String,String> map = new HashMap<>();
    /*
    * 处理逻辑：检查request请求的参数，如果请求的路径不包含/user/*,则过；
    *若包含，则进行检查
    *  若参数和头部都不包含token，输出token不存在的错误信息至页面，并在控制台打印，返回false
    * */
    @Override
    /*它验证所有的请求，但是排除login和静态资源*/
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        System.out.println("=============preHandle============");
        String requestURI=httpServletRequest.getRequestURI();
        if(requestURI.contains("/user/login_success")) {

            return loginHandle(httpServletRequest,httpServletResponse);
        }

        System.out.println("============prehandle===========");

        String token = httpServletRequest.getHeader("token");;
        String username = httpServletRequest.getParameter("username");

        if (token == null || username == null) {
            System.out.println("real token:======================is null");
            String str = "{'errorCode':801,'message':'无法验证token','data':null}";
            dealErrorReturn(httpServletRequest, httpServletResponse, str);
            return false;
        } else {
            if (token != map.get(username)) {
                String str = "{'errorCode':801,'message':'token验证错误','data':null}";
                dealErrorReturn(httpServletRequest, httpServletResponse, str);
                return false;
            }
        }

        token = jwtUtil.updateToken(token);
        map.put(username, token);
        httpServletResponse.setHeader("token", token);
        System.out.println("update token ========================" + token);
        return true;
    }



    /*它只验证login请求*/
    public boolean loginHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        System.out.println("============loginhandle===========");

        String username = httpServletRequest.getParameter("username");
        String token = JwtUtil.generToken(username,"login","user");
        System.out.println(token);
        httpServletResponse.setHeader("token",token);
        httpServletResponse.addCookie(new Cookie("token",token));
        map.put(username,token);

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

    // 检测到没有token，直接返回不验证
    public void dealErrorReturn(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,Object obj){
        String json = (String)obj;
        PrintWriter writer = null;
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html; charset=utf-8");
        try {
            writer = httpServletResponse.getWriter();
            writer.print(json);

        } catch (IOException ex) {
            logger.error("response error",ex);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
}



