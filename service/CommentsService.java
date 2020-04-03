package com.geek.guiyu.service;

import com.geek.guiyu.domain.dataobject.CommentsDTO;
import com.geek.guiyu.domain.exception.NoLoginException;
import com.geek.guiyu.domain.exception.NotAllowCommentException;
import com.geek.guiyu.domain.model.Comments;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;

/**
 * (Comments)表服务接口
 *
 * @author makejava
 * @since 2020-03-31 13:11:05
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface CommentsService {


    /**
     * 得到该文章评论
     * @param request
     * @param coid
     * @return
     * @throws NoLoginException
     */
    List<CommentsDTO> selectComments(HttpServletRequest request, Integer coid) throws NoLoginException;

    /**
     * 发布评论
     * @param request
     * @param commentsDTO
     * @return
     */
    boolean publish(HttpServletRequest request, CommentsDTO commentsDTO) throws ParseException, NotAllowCommentException;


    /**
     * 设置自己的评论不可查看，即status=1;客户端可用***表示
     * @param request
     * @param coid
     * @return
     */
    boolean setDiableView(HttpServletRequest request, Integer coid);

    /**
     * 删除id，即设置deleted=1
     * @param request
     * @param coid
     * @return
     */
    boolean deleteComment(HttpServletRequest request, Integer coid);
}