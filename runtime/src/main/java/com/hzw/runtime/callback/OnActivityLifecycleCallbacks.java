package com.hzw.runtime.callback;

import android.app.Activity;
import android.os.Bundle;

/**
 * author:HZWei
 * date:  2020/8/19
 * desc:
 */
public interface OnActivityLifecycleCallbacks {

    void onActivityCreated(Activity activity, Bundle savedInstanceState);

    void onActivityStarted(Activity activity);

    void onActivityResumed(Activity activity);

    void onActivityPaused(Activity activity);

    void onActivityStopped(Activity activity);

    void onActivitySaveInstanceState(Activity activity, Bundle outState);

    void onActivityDestroyed(Activity activity);
}
