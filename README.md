## LightRxAndroid
- A Rx framework for android

## Sample

- You can write the codes like this:


```
        Observable
                .create(new Func1<String>() {
                    @Override
                    public String call() {
                        String dat = "-1";
                        Log.d(TAG, "create " + dat + " on thread: " + Thread.currentThread().getId());
                        return dat;
                    }
                })
                .subscribeOn(AndroidHandlers.ioThreadHandler())
                .observeOn(AndroidHandlers.computeThreadHandler())
                .map(new Func2<String, Integer>() {
                    @Override
                    public Integer call(String s) throws InterruptException {
                        int ret = Integer.parseInt(s);
                        Log.d(TAG, "map from " + s + " to " + ret + " on thread: " + Thread.currentThread().getId());
                        if (ret < 0) {
                            throw new InterruptException("input not right", ret);
                        }
                        return ret;
                    }
                })
                .observeOn(AndroidHandlers.mainThreadHandler())
                .subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted(Integer integer) {
                        Log.d(TAG, "onCompleted with data " + integer + " on thread: " + Thread.currentThread().getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.toString() + " on thread: " + Thread.currentThread().getId());
                    }
                });
```

## Add LightRxAndroid to your project
<br>
Please ensure that you are using the latest version by checking here
<br>
Gradle:
<br>
```
compile 'com.lhl:light-rx-android:0.0.1'
```

Maven:
```
<dependency>
  <groupId>com.lhl</groupId>
  <artifactId>light-rx-android</artifactId>
  <version>0.0.1</version>
  <type>pom</type>
</dependency>
```

## Why use

- Simple
- Light
