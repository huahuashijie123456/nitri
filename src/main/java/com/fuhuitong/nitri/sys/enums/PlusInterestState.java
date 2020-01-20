package com.fuhuitong.nitri.sys.enums;

/**
 * 加息状态
 */
public enum PlusInterestState {


    NOT_USE(0, "未使用"),

    USE_ED(1, "已使用"),

    CANCEL_ED(2, "作废"),

    OVERDUE(4, "过期");


    private int value;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    PlusInterestState(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
