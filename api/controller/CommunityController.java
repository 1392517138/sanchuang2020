package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.CommunityDTO;
import com.geek.guiyu.service.CommentsService;
import com.geek.guiyu.service.CommunityService;
import com.geek.guiyu.service.util.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @description
 * @date 2020/4/3 5:03 PM
 */
@RestController
@Api(tags = "社区类接口")
@RequestMapping("/community")
public class CommunityController {
    @Autowired
    CommunityService commentsService;

    @PostMapping("/publish")
    @ApiOperation("发布评论。只需传入parentId,text,replyId.parent=(0,1)分别代表自己发布的板块/板块下的评论。replyId=(0,id)分别代表一级评论与回复的评论id号")
    public JSONObject publish(HttpServletRequest request, @RequestBody CommunityDTO communityDTO) throws ParseException {
        return JSONUtils.success(commentsService.publish(request, communityDTO));
    }

    @GetMapping("/getCommunity")
    @ApiOperation("得到社区板块内容,type=(care,recommend,hot)分别对应关注、推荐、热点")
    public JSONObject getCommunity(HttpServletRequest request, @ApiParam("类型") @RequestParam String type, @ApiParam("页码") @RequestParam Integer pageNum) {
        return JSONUtils.success(commentsService.getCommunity(request, type, pageNum));
    }

    @GetMapping("/addViews")
    @ApiOperation("当点击某个社区板块时，传递该id，增加view_num")
    public JSONObject addViews(HttpServletRequest request, @ApiParam("板块的id") @RequestParam Integer parentId) {
        return JSONUtils.success(commentsService.addViews(request, parentId));
    }

    @GetMapping("/addThumb")
    @ApiOperation("给某个社区板块点赞")
    public JSONObject addThumb(HttpServletRequest request, @ApiParam("板块的id") @RequestParam Integer id) {
        return JSONUtils.success(commentsService.addThumb(request, id));
    }
}
