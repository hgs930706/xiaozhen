package com.yishang.xiaozhen.util;

import lombok.Data;

import java.util.Map;

@Data
public class WechatTemplate {

    private String touser;

    private String template_id;

    private String url;

    private Map<String, TemplateData> data;
}
