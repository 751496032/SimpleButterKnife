package com.hzw.runtime.utils;

import android.app.Activity;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * author:HZWei
 * date:  2020/8/12
 * desc:
 */
public class BindingUtils {

    private static final String TAG = BindingUtils.class.getSimpleName();

    private static final String BIND_CLASS_SUFFIX = "_ViewBinding";

    /**
     * 实例化绑定的类
     *
     * @param activity
     */
    public static void bind(Activity activity) {
        try {
            LogUtils.d("bind -> "+ activity.getLocalClassName());
            Class<?> bindingClass = forName(activity);
            Constructor constructor = bindingClass.getConstructor(activity.getClass());
            constructor.newInstance(activity);
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException | ClassNotFoundException e) {
            e.printStackTrace();
            LogUtils.d("bind error -> "+ e.getMessage());
        }
    }


    /**
     * 解绑
     *
     * @param activity
     */
    public static void unbind(Activity activity) {
        try {

            LogUtils.d("unbind -> "+ activity.getLocalClassName());
            Class<?> bindingClass = forName(activity);
            bindingClass.getDeclaredMethod("unbind").invoke(bindingClass.newInstance());
        } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
            LogUtils.d("unbind error -> "+ e.getMessage());
        }

    }

    private static Class<?> forName(Activity activity) throws ClassNotFoundException {
        return Class.forName(activity.getClass().getCanonicalName() + BIND_CLASS_SUFFIX);
    }


}
