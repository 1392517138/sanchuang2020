package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.CommentsDTO;
import com.geek.guiyu.domain.exception.NoLoginException;
import com.geek.guiyu.domain.exception.NotAllowCommentException;
import com.geek.guiyu.domain.model.Comments;
import com.geek.guiyu.service.CommentsService;
import com.geek.guiyu.service.util.JSONUtils;
import com.geek.guiyu.service.util.TokenUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * (Comments)表控制层
 *
 * @author makejava
 * @since 2020-03-31 13:11:05
 */
@RestController
@Api(tags = "评论类接口")
@RequestMapping("/comments")
public class CommentsController {

    /**
     * 服务对象
     */
    @Autowired
    private CommentsService commentsService;

    @GetMapping("/selectComments")
    @ApiOperation("查看该文章下的评论")
    public JSONObject selectComments(HttpServletRequest request, @ApiParam("评论id") @RequestParam Integer coid) throws NoLoginException {
        return JSONUtils.success(commentsService.selectComments(request, coid));
    }

    @PostMapping("/publish")
    @ApiOperation("发表评论")
    private JSONObject publish(HttpServletRequest request, @RequestBody CommentsDTO commentsDTO) throws ParseException, NotAllowCommentException {
        return JSONUtils.success(commentsService.publish(request, commentsDTO));
    }

    @GetMapping("/setDiableView")
    @ApiOperation("设置自己的评论不可查看,即status=1;客户端用***表示")
    private JSONObject setDisableView(HttpServletRequest request, @ApiParam("评论id") @RequestParam Integer coid) {
        return JSONUtils.success(commentsService.setDiableView(request, coid));
    }

    @GetMapping("/deleteComment")
    @ApiOperation("/删除自己的评论")
    private JSONObject deleteComment(HttpServletRequest request, @ApiParam("评论id") @RequestParam Integer coid){
        return JSONUtils.success(commentsService.deleteComment(request, coid));
    }

}