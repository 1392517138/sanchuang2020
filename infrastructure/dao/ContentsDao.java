package com.geek.guiyu.infrastructure.dao;

import com.geek.guiyu.domain.model.Contents;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Contents)表数据库访问层
 *
 * @author makejava
 * @since 2020-04-04 17:16:16
 */
public interface ContentsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param cid 主键
     * @return 实例对象
     */
    Contents queryById(Integer cid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Contents> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param contents 实例对象
     * @return 对象列表
     */
    List<Contents> queryAll(Contents contents);

    /**
     * 新增数据
     *
     * @param contents 实例对象
     * @return 影响行数
     */
    int insert(Contents contents);

    /**
     * 修改数据
     *
     * @param contents 实例对象
     * @return 影响行数
     */
    int update(Contents contents);

    /**
     * 通过主键删除数据
     *
     * @param cid 主键
     * @return 影响行数
     */
    int deleteById(Integer cid);

}