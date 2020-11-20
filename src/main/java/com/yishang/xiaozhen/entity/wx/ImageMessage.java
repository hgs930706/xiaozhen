package com.yishang.xiaozhen.entity.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 回复图片
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ImageMessage extends BaseMessage{

    private Image Image;

}
