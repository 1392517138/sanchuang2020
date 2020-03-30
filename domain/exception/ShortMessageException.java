package com.geek.guiyu.domain.exception;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class ShortMessageException extends Exception{
    public ShortMessageException() {
        super("短信错误，请重新输入");
    }
    @ResponseBody
    @ExceptionHandler(ShortMessageException.class)
    public JSONObject handler(AlreadyRegisterException e){
        return JSONUtils.fail(e);
    }
}
