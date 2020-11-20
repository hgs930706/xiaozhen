package com.yishang.xiaozhen.entity.wx;

import lombok.Data;

/**
 * 图文消息
 */
@Data
public class News {

    private String Title;

    private String Description;

    private String PicUrl;

    /**
     * 点击图文跳转地址
     */
    private String Url;

}
