package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 关注DTO
 * @date 2020/3/17
 * @author 清歌
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FollowDTO implements Serializable {
    private Integer id;
    private String avatarUrl;
    private String nickName;
    private String introduce;
}
