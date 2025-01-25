package com.utek.disasterrelief.demos.enum1;

public enum ResourceNum {
    INSTANT_NOODLES(1, "instantNoodles", "instantNoodles"),
    TOWEL(2, "towel", "towel"),
    TISSUE_PAPER(3, "tissuePaper", "tissuePaper"),
    WATER(4, "water", "water");

    private final int number;
    private final String field;
    private final String label;

    // 构造函数，参数必须和枚举实例一致
    ResourceNum(int number, String field, String label) {
        this.number = number;
        this.field = field;
        this.label = label;
    }

    public int getNumber() {
        return number;
    }

    public String getField() {
        return field;
    }

    public String getLabel() {
        return label;
    }
}
