package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @date 2020/3/31 1:19 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class  CommentsDTO implements Serializable {


    private Integer cid;

    private String author;

    private Date createTime;

    private Integer authorid;

    private Integer ownerid;

    private String ip;

    private String agent;

    private String text;

    private Integer parent;
}
