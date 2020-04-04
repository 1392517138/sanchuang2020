package com.geek.guiyu.infrastructure.dao;

import com.geek.guiyu.domain.model.Community;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description
 * @date 2020/4/4 10:46 AM
 */
public interface CommunityDao {

    @Select("select * from community order by thumb_num desc,create_time desc")
    public List<Community> selectRecommend();

    @Select("select * from community order by view_num desc,create_time desc")
    public List<Community> selectHot();
}
