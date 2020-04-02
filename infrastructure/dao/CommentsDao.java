package com.geek.guiyu.infrastructure.dao;


import com.geek.guiyu.domain.model.Comments;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * (Comments)表数据库访问层
 *
 * @author makejava
 * @since 2020-03-31 13:11:04
 */
public interface CommentsDao {

    /**
     * 通过ID查询单条数据
     *
     * @param coid 主键
     * @return 实例对象
     */
    Comments queryById(Integer coid);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<Comments> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);


    /**
     * 通过实体作为筛选条件查询
     *
     * @param comments 实例对象
     * @return 对象列表
     */
    List<Comments> queryAll(Comments comments);

    /**
     * 新增数据
     *
     * @param comments 实例对象
     * @return 影响行数
     */
    int insert(Comments comments);

    /**
     * 修改数据
     *
     * @param comments 实例对象
     * @return 影响行数
     */
    int update(Comments comments);

    /**
     * 通过主键删除数据
     *
     * @param coid 主键
     * @return 影响行数
     */
    int deleteById(Integer coid);

}