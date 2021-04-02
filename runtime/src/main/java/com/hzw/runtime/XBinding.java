package com.hzw.runtime;

import android.app.Application;

import com.hzw.runtime.bulider.BindingBuilder;
import com.hzw.runtime.callback.OnActivityLifecycleCallbacks;


/**
 * author:HZWei
 * date:  2020/8/19
 * desc:
 */
public class XBinding {

    public static void init(Application application){
        init(application,null);
    }

    public static void init(Application application, OnActivityLifecycleCallbacks callbacks){
        BindingBuilder.INSTANCE.init(application,callbacks);
    }


}
