package com.xegami.pojo.trn;

public class LifeTimeStats {

    private String value;

    private String key;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return "ClassPojo [value = " + value + ", key = " + key + "]";
    }
}
