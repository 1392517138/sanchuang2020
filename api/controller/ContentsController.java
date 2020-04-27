package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.ContentsDTO;
import com.geek.guiyu.service.ContentsService;
import com.geek.guiyu.service.util.JSONUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;

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

    @PostMapping(value = "/publish")
    @ApiOperation("发表文章【可带多个图片】,type=(post,post_draft),用postman测试，设置header中的token,Body中multiFiles以及contentsDTO.eg:{\"allowComments\":0,\"cid\":0,\"password...}")
    public JSONObject publish(HttpServletRequest request, @RequestPart MultipartFile[] multipartFiles, @RequestPart ContentsDTO contentsDTO, Model model) throws ParseException, IOException, FileUploadException {
        return JSONUtils.success(contentsService.publish(request, multipartFiles, contentsDTO, model));
    }

    @GetMapping("/getArticles")
    @ApiOperation("获得自己得发布的/草稿 type=(post,post_draft),无需设置(cid,uid)")
    public JSONObject getDrafs(HttpServletRequest request, @RequestParam String type) {
        return JSONUtils.success(contentsService.getArticles(request, type));
    }

//    @GetMapping("/thumb")
//    @ApiOperation("给文章点赞或者取消点赞,type=(yes,no)分别为点赞与取消点赞")
//    public JSONObject thumb()

    @GetMapping("/addBrowingHistory")
    @ApiOperation("添加浏览记录与该文章浏览人数，当打开某一篇文章时，发送此请求")
    public JSONObject addBrowingHistory(HttpServletRequest request, @ApiParam("文章cid") @RequestParam Integer cid) throws ParseException {
        return JSONUtils.success(contentsService.addBrowingHistory(request, cid));
    }

    @GetMapping("/selectBrowingHistory")
    @ApiOperation("查看浏览记录")
    public JSONObject selectBrowingHistory(HttpServletRequest request) {
        return JSONUtils.success(contentsService.selectBrowingHistory(request));
    }


    @GetMapping("/getTypeArticles")
    @ApiOperation("得到 (所有/自己喜欢/关注的人) 的文章,type=(all,love,care)")
    public JSONObject getLoveArticles(HttpServletRequest request, @RequestParam String type, @ApiParam("页码") @RequestParam Integer pageNum) {
        return JSONUtils.success(contentsService.getTypeArticles(request, type, pageNum));
    }

    @GetMapping("/setArticle")
    @ApiOperation("添加/取消 自己喜欢的文章,type=(add,abolish)")
    public JSONObject setLoveArticle(HttpServletRequest request, @ApiParam("文章cid") @RequestParam Integer cid, @RequestParam String type) throws ParseException {
        return JSONUtils.success(contentsService.setLoveArticle(request, cid, type));
    }

    @GetMapping("/searchContents")
    @ApiOperation("搜索文章，没用es,目前就是按文章的title进行搜搜")
    public JSONObject searchContents(HttpServletRequest request, @RequestParam String title) {
        return JSONUtils.success(contentsService.searchContents(request, title));
    }

}