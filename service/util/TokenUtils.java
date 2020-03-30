package com.geek.guiyu.service.util;

import com.geek.guiyu.domain.dataobject.TokenDTO;
import com.geek.guiyu.domain.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
@Component
public class TokenUtils {
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 生成并把token保存在redis中
     * @param userInfo
     * @return
     */
    public TokenDTO createToken(UserInfo userInfo){
        // 生成uuid
        UUID uuid = UUID.randomUUID();
        // 生成token
        String token = uuid.toString().replace("-", "");
        redisTemplate.opsForValue().set("userToken:" + token, userInfo);

        return new TokenDTO(token);
    }

    public UserInfo getUserInfo(String token){
        return (UserInfo) redisTemplate.opsForValue().get("userToken:" + token);
    }
}
