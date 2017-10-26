package com.ttuicube.dibzitapp.utils;

/**
 * Created by zeejfps on 10/26/17.
 */

public interface PresenterFactory<T extends Presenter> {
    T create();
}
