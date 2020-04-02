package com.geek.guiyu.infrastructure.dao;

import com.geek.guiyu.domain.model.BrowingHistory;
import com.geek.guiyu.domain.model.BrowingHistoryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BrowingHistoryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int countByExample(BrowingHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int deleteByExample(BrowingHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int insert(BrowingHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int insertSelective(BrowingHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    List<BrowingHistory> selectByExample(BrowingHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    BrowingHistory selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int updateByExampleSelective(@Param("record") BrowingHistory record, @Param("example") BrowingHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int updateByExample(@Param("record") BrowingHistory record, @Param("example") BrowingHistoryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int updateByPrimaryKeySelective(BrowingHistory record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 17:51:50 CST 2020
     */
    int updateByPrimaryKey(BrowingHistory record);
}