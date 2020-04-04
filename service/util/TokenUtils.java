package com.geek.guiyu.service.util;

import com.geek.guiyu.domain.dataobject.TokenDTO;
import com.geek.guiyu.domain.model.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class TokenUtils {
    @Autowired
    RedisTemplate redisTemplate;

    @Value("${spring.redis.expireDayTime}")
    private long expireDayTime;

    /**
     * 生成并把token保存在redis中
     *
     * @param userInfo
     * @return
     */
    public TokenDTO createToken(UserInfo userInfo) {
        // 生成uuid
        UUID uuid = UUID.randomUUID();
        // 生成token
        String token = uuid.toString().replace("-", "");
        //存入redis，并设置30天过期
        redisTemplate.opsForValue().set("userToken:" + token, userInfo, expireDayTime, TimeUnit.DAYS);

        return new TokenDTO(token);
    }

    public UserInfo getUserInfo(String token) {
        return (UserInfo) redisTemplate.opsForValue().get("userToken:" + token);
    }

    /**
     * 更新在redis中用户的信息
     *
     * @param token
     * @param userInfo
     */
    public void updateUserInfo(String token, UserInfo userInfo) {
        redisTemplate.opsForValue().getAndSet("userToken:" + token, userInfo);
        log.info(redisTemplate.opsForValue().get("userToken:" + token).toString());
    }
}
