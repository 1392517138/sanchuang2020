package com.geek.guiyu.domain.exception;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.util.JSONUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description
 * @date 2020/4/3 1:56 PM
 */
@ControllerAdvice
public class NotAllowCommentException extends Exception {
    public NotAllowCommentException() {
        super("对不起，该作者不允许评论");
    }

    @ResponseBody
    @ExceptionHandler(NotAllowCommentException.class)
    public JSONObject handler(NotAllowCommentException e) {
        return JSONUtils.fail(e);
    }
}
