package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.ContentsDTO;
import com.geek.guiyu.domain.model.Contents;
import com.geek.guiyu.service.ContentsService;
import com.geek.guiyu.service.util.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apiguardian.api.API;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;

/**
 * (Contents)表控制层
 *
 * @author makejava
 * @since 2020-03-31 13:43:35
 */
@RestController
@Api(tags = "文章类接口")
@RequestMapping("/contents")
public class ContentsController {
    /**
     * 服务对象
     */
    @Autowired
    private ContentsService contentsService;

    @PostMapping("/publish")
    @ApiOperation("发表文章,type=(post,post_draft,atachment)")
    public JSONObject publish(HttpServletRequest request, @RequestParam MultipartFile[] multipartFiles, @RequestBody ContentsDTO contentsDTO, Model model) throws ParseException, IOException, FileUploadException {
        return JSONUtils.success(contentsService.publish(request, multipartFiles, contentsDTO, model));
    }

    @GetMapping("/getArticles")
    @ApiOperation("获得自己得发布的/草稿 type=(post,post_draft)")
    public JSONObject getDrafs(HttpServletRequest request, @RequestParam String type) {
        return JSONUtils.success(contentsService.getArticles(request, type));
    }

//    @GetMapping("/addBrowingHistory")
//    @ApiOperation("添加浏览记录，当打开某一篇文章时，发送此请求")
//    public JSONObject addBrowingHistory(HttpServletRequest request, @ApiParam("文章cid") @RequestParam Integer cid) {
//        return JSONUtils.success(contentsService.addBrowingHistory(request, cid));
//    }


    @GetMapping("/getLoveArticles")
    @ApiOperation("得到自己喜欢的文章")
    public JSONObject getLoveArticles(HttpServletRequest request) {
        return JSONUtils.success(contentsService.getLoveArticles(request));
    }

    @GetMapping("/setArticle")
    @ApiOperation("添加/取消 自己喜欢的文章,type=(add,abolish)")
    public JSONObject setLoveArticle(HttpServletRequest request, @ApiParam("文章cid") @RequestParam Integer cid,@RequestParam String type) throws ParseException {
        return JSONUtils.success(contentsService.setLoveArticle(request, cid, type));
    }

}