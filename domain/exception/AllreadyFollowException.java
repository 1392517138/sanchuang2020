package com.geek.guiyu.domain.exception;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description
 * @date 2020/4/4 6:42 PM
 */
@ControllerAdvice
public class AllreadyFollowException extends Exception {
    public AllreadyFollowException() {
        super("该用户已经关注过了");
    }

    @ResponseBody
    @ExceptionHandler(AllreadyFollowException.class)
    public JSONObject handler(AllreadyFollowException e) {
        return JSONUtils.fail(e);
    }
}
