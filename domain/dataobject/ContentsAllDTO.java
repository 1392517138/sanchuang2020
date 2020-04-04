package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date 2020/4/1 10:30 PM
 */
@Data

@AllArgsConstructor
@ToString
public class ContentsAllDTO extends ContentsDTO implements Serializable {
    //    private String attachmentUrl;
//    private Integer Corder;
    private Map<Integer, String> attachment;

    public ContentsAllDTO() {
        this.attachment = new HashMap<>();
    }
}
