package com.geek.guiyu.api.interceptor;

import com.geek.guiyu.domain.exception.NoLoginException;
import com.geek.guiyu.domain.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    /**
     * 判断用户是否存在redis中
     * @param request
     * @param response
     * @return
     */
    @Autowired
    private RedisTemplate redisTemplate;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 首先在ThreadLocal中判断是否可以获取用户

        String token = request.getHeader("token");
        response.setContentType("application/json;charset=utf-8");
        if(token == null){
            throw new NoLoginException();
        }
        UserInfo userInfo = (UserInfo) redisTemplate.opsForValue().get(token);
        if(userInfo == null){
            throw new NoLoginException();
        }
        // 刷新token
        redisTemplate.expire(token, 7, TimeUnit.DAYS);
        return true;
    }
}
