package com.yishang.xiaozhen.enums;


public enum MsgTypeEnum {

    OTHER_MSG(0,"其它"),
    ACTIVITY_MSG(1,"活动消息"),
    RECEIVE_MSG(2,"接待预约消息"),
    MEETING_MSG(3,"会议场地预约消息");

    private int code;
    private String msg;

    MsgTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getStr(int order_status) {
        for (MsgTypeEnum value : MsgTypeEnum.values()) {
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
