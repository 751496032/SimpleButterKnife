package com.hzw.runtime.utils;

import android.util.Log;

import com.hzw.runtime.BuildConfig;

/**
 * author:HZWei
 * date:  2020/8/20
 * desc:
 */
public class LogUtils {

    private static final String TAG = "x-binding";

    public static void d(String msg){
        d(TAG,msg);
    }

    public static void e(String msg){
        e(TAG,msg);
    }

    public static void d(String tag,String msg){
        if (BuildConfig.DEBUG) return;
        Log.d(tag, msg);
    }

    public static void e(String tag,String msg){
        if (BuildConfig.DEBUG) return;
        Log.e(tag, msg);
    }

}
