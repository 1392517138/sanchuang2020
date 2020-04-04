package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @date 2020/4/3 8:29 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommunityDTO implements Serializable {

    private Date createTime;
    private Date updateTime;
    private String author;
    private Integer authorId;
    private String ip;
    private String agent;
    private Integer parentId;
    private Integer replyid;
    private Integer viewNum;
    private Integer commentNum;
    private Integer thumbNum;
    private String text;
}
