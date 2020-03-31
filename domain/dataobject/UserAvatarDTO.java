package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * @description
 * @date 2020/3/31 9:08 AM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAvatarDTO implements Serializable {
    private MultipartFile multipartFile;
    private String Type;
}
