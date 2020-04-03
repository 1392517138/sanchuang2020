package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @description
 * @date 2020/4/1 10:30 PM
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContentsAllDTO extends ContentsDTO implements Serializable {
    private String attachmentUrl;
    private Integer order;
}
