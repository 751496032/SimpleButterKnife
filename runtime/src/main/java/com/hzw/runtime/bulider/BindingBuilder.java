package com.hzw.runtime.bulider;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.hzw.runtime.callback.OnActivityLifecycleCallbacks;
import com.hzw.runtime.utils.BindingUtils;

/**
 * author:HZWei
 * date:  2020/8/19
 * desc:
 */
public class BindingBuilder {

    public static final BindingBuilder INSTANCE=new BindingBuilder();

    private OnActivityLifecycleCallbacks mCallbacks;

    /**
     * <p>
     * 使用<code>Application.ActivityLifecycleCallbacks</code>做全局绑定，不能直接在<code>Activity.onCreate</code>中调用<code>setContentView</code>来初始化布局；
     * 否则会绑定失败，因为<code>Application.ActivityLifecycleCallbacks</code>的分发回调比<code>Activity.setContentView</code>更先调用
     * 因此，这里提供@Builder注解类的方式，让<code>Activity.setContentView</code>在<code>Application.ActivityLifecycleCallbacks.onActivityCreated</code>方法中实现布局初始化 。
     * 后面在<code>Activity.onCreate</code>中无需再调用<code>Activity.setContentView</code>
     * </p>
     * <code>
     *     @Builder(R.layout.activity_main)
     *     public class MainActivity extends AppCompatActivity {
     *     }
     * </code>
     */
    private Application.ActivityLifecycleCallbacks mActivityLifecycleCallbacks =new Application.ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            BindingUtils.bind(activity);
            if (mCallbacks!=null){
                mCallbacks.onActivityCreated(activity, savedInstanceState);
            }

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (mCallbacks!=null){
                mCallbacks.onActivityStarted(activity);
            }
        }

        @Override
        public void onActivityResumed(Activity activity) {
            if (mCallbacks!=null){
                mCallbacks.onActivityResumed(activity);
            }
        }

        @Override
        public void onActivityPaused(Activity activity) {
            if (mCallbacks!=null){
                mCallbacks.onActivityPaused(activity);
            }
        }

        @Override
        public void onActivityStopped(Activity activity) {
            if (mCallbacks!=null){
                mCallbacks.onActivityStopped(activity);
            }
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            if (mCallbacks!=null){
                mCallbacks.onActivitySaveInstanceState(activity,outState);
            }
        }

        @Override
        public void onActivityDestroyed(Activity activity) {
            BindingUtils.unbind(activity);
            if (mCallbacks!=null){
                mCallbacks.onActivityDestroyed(activity);
            }
        }
    };

    public void init(Application application){
       init(application,null);
    }

    public void init(Application application, OnActivityLifecycleCallbacks callbacks){
        if (application==null) return;
        this.mCallbacks=callbacks;
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallbacks);
    }


}
