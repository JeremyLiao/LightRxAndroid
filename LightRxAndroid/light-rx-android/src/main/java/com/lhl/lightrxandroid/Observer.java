package com.lhl.lightrxandroid;

/**
 * Created by hailiangliao on 2016/10/20.
 */

public interface Observer<T> {
    void onCompleted(T t);

    void onError(Throwable e);
}
