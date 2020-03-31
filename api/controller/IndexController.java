package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.service.ContentsService;
import com.geek.guiyu.service.util.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 主页,推荐pengjie说按最新的排
 */
@RestController
@Api(tags = "主页的接口")
public class IndexController {

    @Autowired
    ContentsService contentsService;

    @GetMapping("/index")
    public JSONObject index() {
        return new JSONObject();
    }

}
