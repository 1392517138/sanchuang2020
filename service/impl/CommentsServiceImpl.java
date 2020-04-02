package com.geek.guiyu.service.impl;

import com.geek.guiyu.domain.dataobject.CommentsDTO;
import com.geek.guiyu.domain.exception.NoLoginException;
import com.geek.guiyu.domain.model.Comments;
import com.geek.guiyu.domain.model.CommentsExample;
import com.geek.guiyu.infrastructure.dao.CommentsDao;
import com.geek.guiyu.infrastructure.dao.CommentsMapper;
import com.geek.guiyu.service.CommentsService;
import com.geek.guiyu.service.util.TimeUtils;
import com.geek.guiyu.service.util.TokenUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

/**
 * (Comments)表服务实现类
 *
 * @author makejava
 * @since 2020-03-31 13:11:05
 */
@Service
public class CommentsServiceImpl implements CommentsService {
    @Autowired
    private TokenUtils tokenUtils;
    @Resource
    private CommentsMapper commentsMapper;
    @Resource
    private CommentsDao commentsDao;
    @Autowired
    private Mapper dozerMapper;



    /**
     * 得到该id下的评论
     * @param request
     * @param coid
     */
    @Override
    public List<CommentsDTO> selectComments(HttpServletRequest request, Integer coid) throws NoLoginException {
        String token = request.getHeader("token");
        //如果没有登陆，抛出异常
        if (tokenUtils.getUserInfo(token)==null){
            throw new NoLoginException();
        }
        CommentsExample commentsExample = new CommentsExample();
        CommentsExample.Criteria criteria = commentsExample.createCriteria();
        criteria.andCidEqualTo(coid);
        //查询未删除
        criteria.andDeletedEqualTo((byte)0);

        List<Comments> comments = commentsMapper.selectByExample(commentsExample);
        List<CommentsDTO> commentsDTOS = new LinkedList<>();
        for(Comments comment: comments){
            commentsDTOS.add(dozerMapper.map(comment, CommentsDTO.class));
        }
        return commentsDTOS;
    }

    /**
     * 发布评论
     * @param request
     * @param commentsDTO
     * @return
     * @throws ParseException
     */
    @Override
    public boolean publish(HttpServletRequest request, CommentsDTO commentsDTO) throws ParseException {
        //1.插入comments表
        String token = request.getHeader("token");
        Comments comments = dozerMapper.map(commentsDTO, Comments.class);
        comments.setIp(request.getRemoteAddr());
        comments.setAgent(request.getHeader("user-agent"));
        comments.setCreateTime(TimeUtils.getTime("ss"));
        comments.setDeleted((byte)0);
        comments.setStatus((byte)0);
        commentsMapper.insert(comments);
        //2.更新contents中comments_num
        Integer cid = commentsDTO.getCid();

        return true;
    }

    /**
     * 设置评论不可见
     * @param request
     * @param coid
     * @return
     */
    @Override
    public boolean setDiableView(HttpServletRequest request, Integer coid) {
        Comments comments = commentsDao.queryById(coid);
        comments.setStatus((byte)1);
        commentsDao.update(comments);
        return true;
    }

    /**
     * 设置删除评论
     * @param request
     * @param coid
     * @return
     */
    @Override
    public boolean deleteComment(HttpServletRequest request, Integer coid) {
        Comments comments = commentsDao.queryById(coid);
        comments.setDeleted((byte)1);
        commentsDao.update(comments);
        return false;
    }

}