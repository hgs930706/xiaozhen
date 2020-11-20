package com.yishang.xiaozhen.entity.wx;

import lombok.Data;

@Data
public class AccessToken {

    private String accessToken;

    private int expiresIn;

}
