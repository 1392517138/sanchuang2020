package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.CommunityDTO;
import com.geek.guiyu.domain.dataobject.FollowDTO;
import com.geek.guiyu.domain.model.Community;
import com.geek.guiyu.domain.model.CommunityExample;
import com.geek.guiyu.domain.model.UserInfo;
import com.geek.guiyu.infrastructure.dao.CommunityDao;
import com.geek.guiyu.infrastructure.dao.CommunityMapper;
import com.geek.guiyu.service.CommunityService;
import com.geek.guiyu.service.UserService;
import com.geek.guiyu.service.util.TimeUtils;
import com.geek.guiyu.service.util.TokenUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @description
 * @date 2020/4/3 9:02 PM
 */
@Service
public class CommunityServiceImpl implements CommunityService {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private Mapper dozerMapper;
    @Autowired
    private CommunityMapper communityMapper;
    @Autowired
    private CommunityDao communityDao;
    @Autowired
    private UserService userService;

    @Value("${pagehelper.pageSize}")
    private int pageSize;

    /**
     * 发布板块，或是评论
     *
     * @param request
     * @param communityDTO
     * @return
     */
    @Override
    public boolean publish(HttpServletRequest request, CommunityDTO communityDTO) throws ParseException {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        Community community = dozerMapper.map(communityDTO, Community.class);
        community.setCreateTime(TimeUtils.getTime("ss"));
        community.setUpdateTime(TimeUtils.getTime("ss"));
        community.setAuthor(userInfo.getNickName());
        community.setAuthorId(userInfo.getId());
        community.setViewNum(0);
        community.setCommentNum(0);
        community.setThumbNum(0);

        communityMapper.insert(community);
        return true;
    }

    /**
     * 添加观看人数
     *
     * @param request
     * @param id
     * @return
     */
    @Override
    public boolean addViews(HttpServletRequest request, Integer id) {
        Community community = communityMapper.selectByPrimaryKey(id);
        community.setViewNum(community.getViewNum() + 1);
        communityMapper.updateByPrimaryKey(community);
        return true;
    }

    /**
     * 得到社区板块内容,type=(care,recommend,hot)分别对应关注、推荐、热点
     *
     * @param request
     * @param type
     * @param pageNum
     * @return
     */
    @Override
    public PageInfo getCommunity(HttpServletRequest request, String type, Integer pageNum) {
        String token = request.getHeader("token");
        UserInfo userInfo = tokenUtils.getUserInfo(token);
        PageHelper.startPage(pageNum, pageSize);
        CommunityExample communityExample = new CommunityExample();
        CommunityExample.Criteria criteria = communityExample.createCriteria();
        //创建返回列表
        final List<Community> communities = new LinkedList<>();
        //如果获取关注
        if ("care".equals(type)) {
            //获得关注的对象列表
            List<FollowDTO> followDTOS = userService.queryFollows(token);
            followDTOS.parallelStream().forEach((followDTO -> {
                criteria.andAuthorIdEqualTo(followDTO.getId());

//                criteria.andParentIdEqualTo(0);
                //添加进列表
                communities.addAll(communityMapper.selectByExampleWithBLOBs(communityExample));
                sortByTimeDesc(communities);
            }));
        }
        if ("recommend".equals(type)) {
            communities.addAll(communityDao.selectRecommend());
        }
        if ("hot".equals(type)) {
            communities.addAll(communityDao.selectHot());
        }
        //分页
        PageInfo pageInfo = new PageInfo(communities);

        return pageInfo;
    }

    private void sortByTimeDesc(List<Community> communities) {
        //按照时间降序排序
        Collections.sort(communities, (c1, c2) -> {
            return c2.getCreateTime().compareTo(c1.getCreateTime());
        });
    }

    /**
     * 给某个社区板块点赞
     *
     * @param request
     * @param id
     * @return
     */
    @Override
    public boolean addThumb(HttpServletRequest request, Integer id) {
        Community community = communityMapper.selectByPrimaryKey(id);
        community.setThumbNum(community.getThumbNum() + 1);
        communityMapper.updateByPrimaryKey(community);
        return true;
    }
}
