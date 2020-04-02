package com.geek.guiyu.service;

import com.geek.guiyu.domain.dataobject.ContentsAllDTO;
import com.geek.guiyu.domain.dataobject.ContentsDTO;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;

/**
 * (Contents)表服务接口
 *
 * @author makejava
 * @since 2020-03-31 13:43:35
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface ContentsService {



    /**
     * 发表文章
     * @param request
     * @param multipartFiles
     * @param contentsDTO
     * @param model
     * @return
     */
    boolean publish(HttpServletRequest request, MultipartFile[] multipartFiles, ContentsDTO contentsDTO, Model model) throws ParseException, IOException, FileUploadException;

    /**
     * 获得所有得草稿
     * @param request
     * @return ContentsAllDTO为ContentsDTO加上
     */
    List<ContentsAllDTO> getArticles(HttpServletRequest request,String type);

    /**
     * 得到喜欢的文章
     * @param request
     * @return
     */
    List<ContentsAllDTO> getLoveArticles(HttpServletRequest request);

    /**
     * 添加/取消喜欢的文章
     * @param request
     * @param cid
     * @param type
     * @return
     */
    boolean setLoveArticle(HttpServletRequest request, Integer cid, String type) throws ParseException;
}