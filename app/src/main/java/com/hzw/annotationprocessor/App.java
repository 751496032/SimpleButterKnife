package com.hzw.annotationprocessor;

import android.app.Application;

import com.hzw.runtime.XBinding;

/**
 * author:HZWei
 * date:  2020/8/19
 * desc:
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        XBinding.init(this);
    }
}
