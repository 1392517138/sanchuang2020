package com.geek.guiyu.service;

import com.geek.guiyu.domain.dataobject.UserFileDTO;
import com.geek.guiyu.domain.model.UserFile;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface UploadService {

    /**
     * modify by piwenjing
     * @param userFileDTO
     * @param request
     * @param file
     * @param model
     * @return
     * @throws IOException
     * @throws FileUploadException
     */
    String uploadImage(UserFileDTO userFileDTO, HttpServletRequest request, MultipartFile file, Model model) throws IOException, FileUploadException;

    /**
     * 查看用户的上传文件
     * @param token 用户token
     * @return
     */
    List<UserFile> queryUserFile(String token);
}
