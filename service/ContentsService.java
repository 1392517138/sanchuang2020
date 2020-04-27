package com.geek.guiyu.service;

import com.geek.guiyu.domain.dataobject.BrowingHistoryDTO;
import com.geek.guiyu.domain.dataobject.ContentsAllDTO;
import com.geek.guiyu.domain.dataobject.ContentsDTO;
import com.geek.guiyu.domain.model.Contents;
import com.github.pagehelper.PageInfo;
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
     * 根据cid获得文章
     *
     * @param cid
     * @return
     */
    Contents getContentByCid(Integer cid);

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
     * 获得所有的  自己发布的/草稿
     * @param request
     * @return ContentsAllDTO为ContentsDTO加上
     */
    List<ContentsAllDTO> getArticles(HttpServletRequest request,String type);

    /**
     * 得到 (所有/自己喜欢/关注的人) 的文章,type=(all,love,care)
     * @param request
     * @param type
     * @return
     */
    PageInfo getTypeArticles(HttpServletRequest request, String type, Integer pageNum);

    /**
     * 添加/取消喜欢的文章
     * @param request
     * @param cid
     * @param type
     * @return
     */
    boolean setLoveArticle(HttpServletRequest request, Integer cid, String type) throws ParseException;

    /**
     * 当点击某一个文章时，添加浏览记录至browing_history表
     *
     * @param request
     * @param cid
     * @return
     */
    boolean addBrowingHistory(HttpServletRequest request, Integer cid) throws ParseException;

    /**
     * 查询用户的浏览记录
     *
     * @param request
     * @return
     */
    List<BrowingHistoryDTO> selectBrowingHistory(HttpServletRequest request);

    List<ContentsAllDTO> searchContents(HttpServletRequest request, String title);
}