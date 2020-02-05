/*
author:chxy
data:2020/2/4
description:
*/
package com.bcd.springmvc.token;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by ThinkPad on 2017/6/20.
 */
public class HeaderTokenInterceptor implements HandlerInterceptor {

    private final static Logger logger = LoggerFactory.getLogger(HeaderTokenInterceptor.class);
    JwtUtil jwtUtil = new JwtUtil();
    /*
    * 处理逻辑：检查request请求的参数，如果请求的路径不包含/user/*,则过；
    *若包含，则进行检查
    *  若参数和头部都不包含token，输出token不存在的错误信息至页面，并在控制台打印，返回false
    *
    * */
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // String  contentPath=httpServletRequest.getContextPath();
        // System.out.println("contenxPath:"+contentPath);
        String requestURI=httpServletRequest.getRequestURI();
        String tokenStr=httpServletRequest.getParameter("token");
        String token = "";
        if(requestURI.contains("/user/")){
            token=httpServletRequest.getHeader("token");
            if(token==null && tokenStr==null){
                System.out.println("real token:======================is null");
                String str="{'errorCode':801,'message':'缺少token，无法验证','data':null}";
                dealErrorReturn(httpServletRequest,httpServletResponse,str);
                return false;
            }
            if(tokenStr!=null){
                token=tokenStr;
            }
            token=jwtUtil.updateToken(token);
            System.out.println("real token:=============================="+token);
            System.out.println("real ohter:=============================="+httpServletRequest.getHeader("Cookie"));
        }

        httpServletResponse.setHeader("token",token);
        System.out.println("i want to see token:"+token);
      /*  httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Authorization");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, PUT");*/
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

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



