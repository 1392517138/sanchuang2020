package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @description
 * @date 2020/4/3 9:30 AM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BrowingHistoryDTO implements Serializable {

    private Integer id;


    private Integer cid;


    private Integer uid;


    private Date createTime;
}
