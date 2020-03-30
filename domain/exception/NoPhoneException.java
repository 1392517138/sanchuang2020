package com.geek.guiyu.domain.exception;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
@ControllerAdvice
public class NoPhoneException extends Exception{
    public NoPhoneException() {
    super("改手机号未注册，请注册");
}
    @ResponseBody
    @ExceptionHandler(NoPhoneException.class)
    public JSONObject handler(AlreadyRegisterException e){
        return JSONUtils.fail(e);
    }

}
