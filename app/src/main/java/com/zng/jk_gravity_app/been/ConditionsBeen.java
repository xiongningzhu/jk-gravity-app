package com.zng.jk_gravity_app.been;

public class ConditionsBeen {
    private String Label;
    private String Value;
    private Boolean isChoose=false;

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    public String getValue() {
        return Value;
    }

    public void setValue(String value) {
        Value = value;
    }

    public Boolean getChoose() {
        return isChoose;
    }

    public void setChoose(Boolean choose) {
        isChoose = choose;
    }
}
