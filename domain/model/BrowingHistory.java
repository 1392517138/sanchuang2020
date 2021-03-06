package com.geek.guiyu.domain.model;

import java.io.Serializable;
import java.util.Date;

public class BrowingHistory implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column browing_history.id
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column browing_history.cid
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    private Integer cid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column browing_history.uid
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    private Integer uid;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column browing_history.create_time
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    private Date createTime;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    private static final long serialVersionUID = 1L;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column browing_history.id
     *
     * @return the value of browing_history.id
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public Integer getId() {
        return id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column browing_history.id
     *
     * @param id the value for browing_history.id
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column browing_history.cid
     *
     * @return the value of browing_history.cid
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public Integer getCid() {
        return cid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column browing_history.cid
     *
     * @param cid the value for browing_history.cid
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public void setCid(Integer cid) {
        this.cid = cid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column browing_history.uid
     *
     * @return the value of browing_history.uid
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public Integer getUid() {
        return uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column browing_history.uid
     *
     * @param uid the value for browing_history.uid
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column browing_history.create_time
     *
     * @return the value of browing_history.create_time
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column browing_history.create_time
     *
     * @param createTime the value for browing_history.create_time
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table browing_history
     *
     * @mbggenerated Thu Apr 02 23:23:00 CST 2020
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", cid=").append(cid);
        sb.append(", uid=").append(uid);
        sb.append(", createTime=").append(createTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}