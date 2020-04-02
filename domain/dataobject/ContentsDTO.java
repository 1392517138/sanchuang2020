package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 * @description
 * @date 2020/3/31 3:23 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsDTO {

    /**
     * 文章cid
     */
    private Integer cid;
    /**
     * 标题
     */
    private String title;


    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 内容
     */
    private String text;

    /**
     * 类型:post;post_draft草稿;atachment附件
     */
    private String type;

    /**
     * 加密密码
     */
    private String password;



    /**
     * 0为不允许;1为允许
     */
    private Byte allowComments;

}
