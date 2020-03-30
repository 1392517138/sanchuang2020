package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEditInfoDTO implements Serializable {
    private String nickName;
    private Byte sex;
    private Date birthday;
    private String location;
    private String school;
    private String occupation;
    private String introduce;
    private String backgroundImage;
    private String avatarUrl;
}
