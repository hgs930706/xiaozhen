package com.yishang.xiaozhen.enums;

/**
 *  '预约状态(0待审批，1审批通过，2审批拒绝)'
 */
public enum ApprovalStatusEnum {

    WAIT_APPROVAL(0,"待审批"),
    SUCCESS_APPROVAL(1,"审批通过"),
    FAIL_APPROVAL(2,"审批拒绝");

    private int code;
    private String msg;

    ApprovalStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getStr(int order_status) {
        for (ApprovalStatusEnum value : ApprovalStatusEnum.values()) {
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
