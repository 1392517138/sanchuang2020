package com.geek.guiyu.service.util;

import com.alibaba.fastjson.JSONObject;

public class JSONUtils {
    private static final String SUCCESS_MSG = "success";
    private static final String FAIL_MSG = "fail";
    public static JSONObject success(Object data){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", SUCCESS_MSG);
        jsonObject.put("code", "1");
        jsonObject.put("data", data);
        return jsonObject;
    }
    public static JSONObject fail(Exception e){
        e.printStackTrace();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", FAIL_MSG);
        jsonObject.put("code", "0");
        jsonObject.put("Exception", e.getMessage());
        return jsonObject;
    }
}
