package com.geek.guiyu.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserFileDTO implements Serializable {
    private String topic;
    private String describe;
    private static final long serialVersionUID = -362566296742773252L;
}
