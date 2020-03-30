package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主页
 */
@RestController
public class IndexController {
    @GetMapping("/index")
    public JSONObject index() {
        return new JSONObject();
    }
}
