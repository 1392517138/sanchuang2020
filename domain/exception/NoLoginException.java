package com.geek.guiyu.domain.exception;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class NoLoginException extends Exception{
    public NoLoginException() {
        super("对不起，请登录");
    }
    @ResponseBody
    @ExceptionHandler(NoLoginException.class)
    public JSONObject handler(NoLoginException e){
        return JSONUtils.fail(e);
    }
}
