/*
author:chxy
data:2020/2/4
description:
*/
package com.bcd.springmvc.configure;


import com.bcd.springmvc.token.HeaderTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getTokenHeader())
                .addPathPatterns("/user/*")
                .excludePathPatterns(
                        "/robots.txt");
    }

    //token 在header的拦截器
    @Bean
    public HandlerInterceptor getTokenHeader(){
        return new HeaderTokenInterceptor();
    }
}



