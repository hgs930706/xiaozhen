package com.yishang.xiaozhen.entity.wx;

import lombok.Data;

@Data
public class AccessToken {

    private String accessToken;

    private long expiresTime;


    public AccessToken(String accessToken, String expireIn) {
        this.accessToken = accessToken;
        this.expiresTime = System.currentTimeMillis() + Integer.parseInt(expireIn) * 1000;
    }

    public boolean isExpired(){
        return System.currentTimeMillis() > expiresTime;
    }
}
