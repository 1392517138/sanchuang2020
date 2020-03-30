package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 粉丝DTO
 * @date 2020/3/17
 * @author 清歌
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FansDTO implements Serializable {
    private Integer id;
    private String avatarUrl;
    private String nickName;
    private String introduce;
    private Byte isFollow;
}
