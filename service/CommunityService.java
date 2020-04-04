package com.geek.guiyu.service;

import com.geek.guiyu.domain.dataobject.CommunityDTO;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

/**
 * @description
 * @date 2020/4/3 8:43 PM
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface CommunityService {


    /**
     * 发布板块，或是评论
     *
     * @param request
     * @param communityDTO
     * @return
     */
    boolean publish(HttpServletRequest request, CommunityDTO communityDTO) throws ParseException;

    /**
     * 添加观看人数
     *
     * @param request
     * @param id
     * @return
     */
    boolean addViews(HttpServletRequest request, Integer id);

    /**
     * 得到社区板块内容,type=(care,recommend,hot)分别对应关注、推荐、热点
     *
     * @param request
     * @param type
     * @param pageNum
     * @return
     */
    PageInfo getCommunity(HttpServletRequest request, String type, Integer pageNum);

    /**
     * 给某个社区板块点赞
     *
     * @param request
     * @param parentId
     * @return
     */
    boolean addThumb(HttpServletRequest request, Integer id);
}
