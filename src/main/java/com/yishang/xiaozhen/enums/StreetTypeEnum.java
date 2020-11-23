package com.yishang.xiaozhen.enums;


public enum StreetTypeEnum {

    ONE_STREET(1,"时尚文化街区"),
    TWO_STREET(2,"时尚艺术街区"),
    THREE_STREET(3,"时尚历史街区"),
    FOUR_STREET(4,"瑞丽轻奢街区");

    private int code;
    private String msg;

    StreetTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getStr(int order_status) {
        for (StreetTypeEnum value : StreetTypeEnum.values()) {
            if (value.getCode() == order_status) {
                return value.getMsg();
            }
        }
        return "";
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
