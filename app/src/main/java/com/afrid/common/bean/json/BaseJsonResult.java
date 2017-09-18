package com.afrid.common.bean.json;

/**
 * 功能：
 *
 * @author yu
 * @date 2017/8/9.
 */
public class BaseJsonResult<T> {

    private int resultCode;
    private String msg;
    private T resultData;

    public BaseJsonResult() {

    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getResultData() {
        return resultData;
    }

    public void setResultData(T resultData) {
        this.resultData = resultData;
    }
}
