package com.fuhuitong.nitri.common.utils.Ud.enums;

public enum UdEnums {


    RET_CODE_SUCCESS("000000", "操作成功"),

    SUCCESS_STATUS("1", "认证一致");

    private String value;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    UdEnums(String value, String name) {
        this.value = value;
        this.name = name;
    }
}
