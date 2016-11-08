package com.lhl.lightrxandroid;

/**
 * Created by hailiangliao on 2016/11/7.
 */

public class InterruptException extends Exception {

    private Object data;

    public InterruptException(String detailMessage, Object data) {
        super(detailMessage);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
