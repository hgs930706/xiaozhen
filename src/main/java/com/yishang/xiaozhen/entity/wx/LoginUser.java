package com.yishang.xiaozhen.entity.wx;

import lombok.Data;

@Data
public class LoginUser {

    private String username;

    private String password;
//    映射字段时排除不必要的字段，忽略字段
//    @TableField(exist = false)
}
