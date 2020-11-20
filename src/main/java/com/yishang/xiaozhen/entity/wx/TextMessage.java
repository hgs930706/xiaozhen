package com.yishang.xiaozhen.entity.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 回复文本消息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TextMessage extends BaseMessage{

    /**
     * 消息内容
     */
    private String Content;

    /**
     * 消息id
     */
    private String MsgId;


}
