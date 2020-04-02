package com.geek.guiyu.domain.model.entity;

import java.util.Date;
import java.io.Serializable;

/**
 * (Contents)实体类
 *
 * @author makejava
 * @since 2020-04-01 20:47:57
 */
public class Contents implements Serializable {
    private static final long serialVersionUID = -51931596307618255L;
    /**
    * 文章id
    */
    private Integer cid;
    /**
    * 标题
    */
    private String title;
    
    private Date createTime;
    
    private Date updateTime;
    /**
    * 内容
    */
    private String text;
    /**
    * 用户id
    */
    private Integer uid;
    /**
    * 类型:post;post_draft草稿;atachment附件
    */
    private String type;
    /**
    * 状态;0为未发布;1为发布
    */
    private Byte status;
    /**
    * 加密密码
    */
    private String password;
    /**
    * 评论数量
    */
    private Integer commentsNum;
    /**
    * 所属文章
    */
    private Integer parent;
    /**
    * 点赞人数
    */
    private Integer loveCount;
    /**
    * 浏览数量
    */
    private Integer views;
    /**
    * 0为不允许;1为允许
    */
    private Byte allowComments;
    /**
    * 0为非热榜;1为热榜
    */
    private Byte isHot;


    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCommentsNum() {
        return commentsNum;
    }

    public void setCommentsNum(Integer commentsNum) {
        this.commentsNum = commentsNum;
    }

    public Integer getParent() {
        return parent;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public Integer getLoveCount() {
        return loveCount;
    }

    public void setLoveCount(Integer loveCount) {
        this.loveCount = loveCount;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Byte getAllowComments() {
        return allowComments;
    }

    public void setAllowComments(Byte allowComments) {
        this.allowComments = allowComments;
    }

    public Byte getIsHot() {
        return isHot;
    }

    public void setIsHot(Byte isHot) {
        this.isHot = isHot;
    }

}