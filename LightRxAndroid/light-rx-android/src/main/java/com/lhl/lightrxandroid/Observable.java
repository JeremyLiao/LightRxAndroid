package com.lhl.lightrxandroid;

import android.os.Handler;

/**
 * Created by hailiangliao on 2016/10/20.
 */

public class Observable<T> implements Runnable {

    public final static <T> Observable<T> create(Func1<T> func1) {
        return new Observable<>(func1);
    }

    private Func1<T> func1;
    private Func2 func2;

    private Object input;
    private Object result;

    private Observable previous = null;
    private Observable next = null;

    private Observer observer;

    private Handler observeHandler;
    private Handler subscribeHandler;

    private Observable(Func1<T> func1) {
        this.func1 = func1;
        subscribeHandler = AndroidHandlers.computeThreadHandler();
    }

    private Observable(Func2<Object, T> func2) {
        this.func2 = func2;
    }

    public final void subscribe(Observer<? super T> observer) {
        this.observer = observer;
        Observable root = this;
        while (root.previous != null) {
            root = root.previous;
        }
        root.subscribeHandler.post(root);
    }

    public final <R> Observable<R> map(Func2<? super T, ? extends R> func2) {
        next = new Observable(func2);
        next.previous = this;
        if (observeHandler != null) {
            next.subscribeHandler = observeHandler;
        } else {
            next.subscribeHandler = subscribeHandler;
        }
        return next;
    }

    public Observable<T> subscribeOn(Handler handler) {
        subscribeHandler = handler;
        return this;
    }

    public Observable<T> observeOn(Handler handler) {
        observeHandler = handler;
        return this;
    }

    private Handler getObserveExecuteHandler(Observable observable) {
        Handler handler = observable.observeHandler;
        if (handler == null) {
            handler = observable.subscribeHandler;
        }
        return handler;
    }

    @Override
    public void run() {
        try {
            if (func1 != null) {
                result = func1.call();
            } else if (func2 != null) {
                result = func2.call(input);
            }
            if (next != null) {
                next.input = result;
                if (next.subscribeHandler == null) {
                    next.subscribeHandler = subscribeHandler;
                }
                next.subscribeHandler.post(next);
            } else {
                if (observer != null) {
                    Handler handler = getObserveExecuteHandler(this);
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            observer.onCompleted(result);
                        }
                    });
                }
            }
        } catch (final Throwable e) {
            Observable last = this;
            while (last.next != null) {
                last = next;
            }
            if (last.observer != null) {
                Handler handler = getObserveExecuteHandler(last);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        observer.onError(e);
                    }
                });
            }
        }
    }
}
