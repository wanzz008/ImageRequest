package com.wzz.imagerequest.lifecycle;

import com.wzz.imagerequest.cache.DoubleLruCache;

public class LifecycleObservable {
    private static LifecycleObservable instance;

    public static LifecycleObservable getInstance() {
        if (instance == null) {
            synchronized (LifecycleObservable.class) {
                if (instance == null) {
                    instance = new LifecycleObservable();
                }
            }
        }
        return instance;
    }
    public void onStart(int activityCode) {


    }
    public void onStop(int activityCode) {

    }
//    回收
    void onDestroy(int activityCode) {
        DoubleLruCache.getInstance().remove(activityCode);
    }



}
