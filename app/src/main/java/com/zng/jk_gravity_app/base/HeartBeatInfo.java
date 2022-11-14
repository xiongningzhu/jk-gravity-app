package com.zng.jk_gravity_app.base;

public class HeartBeatInfo {
    /**
     * Code : 200
     * Msg : string
     * Data : true
     */

    private int Code;
    private String Msg;
    private boolean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public boolean isData() {
        return Data;
    }

    public void setData(boolean Data) {
        this.Data = Data;
    }
}
