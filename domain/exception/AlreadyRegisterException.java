package com.geek.guiyu.domain.exception;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class AlreadyRegisterException extends Exception{
    public AlreadyRegisterException() {
        super("该手机号已注册，请直接登录");
    }
    @ResponseBody
    @ExceptionHandler(AlreadyRegisterException.class)
    public JSONObject handler(AlreadyRegisterException e){
        return JSONUtils.fail(e);
    }
}
