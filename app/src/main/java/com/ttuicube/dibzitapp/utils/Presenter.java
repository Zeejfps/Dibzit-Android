package com.ttuicube.dibzitapp.utils;

/**
 * Created by zeejfps on 10/26/17.
 */

public interface Presenter<V> {
    void onViewAttached(V view);
    void onViewDetached();
    void onDestroyed();
}
