package com.geek.guiyu.infrastructure.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

/**
 * @description
 * @date 2020/4/2 11:26 PM
 */

public interface LoveContentsDao {

    /**
     * 根据uid与cid设置deleted
     *
     * @param deleted
     * @param uid
     * @param cid
     * @param updateTime
     */
    @Update("update love_contents set deleted=#{deleted},update_time=#{updateTime}  where uid=#{uid} and cid=#{cid}")
    void updateDeletedByUidAndCid(@Param("deleted") byte deleted, @Param("uid") Integer uid, @Param("cid") Integer cid, @Param("updateTime") Date updateTime);

    /**
     * 寻找是否存在有用户uid收藏cid
     *
     * @param uid
     * @param cid
     * @return
     */
    @Select("select count(*) from love_contents where uid=#{uid} and cid=#{cid}")
    int selectLoveExists(@Param("uid") Integer uid, @Param("cid") Integer cid);

    /**
     * 查询出用户喜欢的文章cid列表
     *
     * @param uid
     * @return
     */
    @Select("select cid from love_contents where deleted=0 and uid=#{uid}")
    List<Integer> selectLoveCids(@Param("uid") Integer uid);
}
