package com.yishang.xiaozhen.util;

import lombok.Data;

@Data
public class TemplateData {

    private String value;

    private String color;

    public TemplateData() {
    }

    public TemplateData(String value) {
        this.value = value;
    }

}
