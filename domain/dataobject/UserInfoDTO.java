package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDTO implements Serializable {

    private Date createTime;

    private Date updateTime;

    private Byte deleted;

    private String account;

    private String phone;

    private String password;

    private String avatarUrl;

    private Integer fans;

    private String nickName;

    private Byte sex;

    private Date birthday;

    private String location;

    private String school;

    private String occupation;

    private String introduce;

    private String backgroundImage;
}
