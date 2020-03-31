package com.geek.guiyu.service;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description
 * @date 2020/3/30 11:11 PM
 */
@Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public interface ContentsService {
    /**
     * 得到首页推荐,指定数量
     * @return
     */

}
