package com.fuhuitong.nitri.sys.enums;


/**
 * @Author Wang
 * @Date 2019/4/19 0019 10:22
 **/

public enum ApprovalState {
    /**
     * 审批状态
     */
    APPROVAL_WAIT(0, "待审批"),


    APPROVAL_OK(1, "审批通过"),

    APPROVAL_REJECT(-1, "审核拒绝");

    private int code;
    private String msg;

    ApprovalState(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}

