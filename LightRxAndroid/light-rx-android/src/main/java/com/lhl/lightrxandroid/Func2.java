package com.lhl.lightrxandroid;

/**
 * Created by hailiangliao on 2016/10/21.
 */

public interface Func2<T, R> {
    R call(T t) throws InterruptException;
}
