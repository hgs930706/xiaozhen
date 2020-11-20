package com.yishang.xiaozhen.entity.wx;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 回复图文消息
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NewsMessage extends BaseMessage {

    private int ArticleCount;

    private List<News> Articles;

}
