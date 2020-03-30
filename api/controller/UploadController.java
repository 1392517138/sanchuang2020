package com.geek.guiyu.api.controller;

import com.alibaba.fastjson.JSONObject;
import com.geek.guiyu.domain.dataobject.UserFileDTO;
import com.geek.guiyu.service.UploadService;
import com.geek.guiyu.service.util.JSONUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
public class UploadController {
    @Autowired
    private UploadService uploadService;

    /**
     * 上传文件Controller层
     * @param userFileDTO
     * @param request
     * @param file
     * @param model
     * @return
     * @throws IOException
     * @throws FileUploadException
     */
    @PostMapping("/upload")
    public JSONObject uploadFile(UserFileDTO userFileDTO, HttpServletRequest request, @Param("file") MultipartFile file, Model model) throws IOException, FileUploadException {
        return JSONUtils.success(uploadService.uploadImage(userFileDTO, request, file, model));
    }

    @GetMapping("/userFile")
    public JSONObject queryFile(HttpServletRequest request){
        String token = request.getHeader("token");
        return JSONUtils.success(uploadService.queryUserFile(token));
    }
}
