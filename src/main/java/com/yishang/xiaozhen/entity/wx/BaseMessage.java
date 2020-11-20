package com.yishang.xiaozhen.entity.wx;

import lombok.Data;

/**
 * 消息回复基类
 */
@Data
public class BaseMessage {

    private String ToUserName;

    private String FromUserName;

    private long CreateTime;

    private String MsgType;

}
