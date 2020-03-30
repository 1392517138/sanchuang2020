package com.geek.guiyu.infrastructure.configuration;

import com.geek.guiyu.api.interceptor.TokenInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    TokenInterceptor tokenInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] exclude = {"/","/druid/*","/login", "/error", "/register", "/getShortMessage"};
        registry.addInterceptor(tokenInterceptor)                   //添加拦截器
                .excludePathPatterns(exclude)//对应的不拦截的请求
                .addPathPatterns("/**")  ;
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/upload/**").addResourceLocations("file:E:/upload/");
    }
}
