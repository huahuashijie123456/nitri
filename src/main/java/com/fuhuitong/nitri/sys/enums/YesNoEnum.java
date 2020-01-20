package com.fuhuitong.nitri.sys.enums;

public enum YesNoEnum {

    YES(1, "是 已删除"),


    NO(0, "否 未删除");

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

    YesNoEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }
}
