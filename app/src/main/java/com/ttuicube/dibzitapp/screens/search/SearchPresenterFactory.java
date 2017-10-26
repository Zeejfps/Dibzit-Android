package com.ttuicube.dibzitapp.screens.search;

import com.ttuicube.dibzitapp.utils.PresenterFactory;

/**
 * Created by zeejfps on 10/26/17.
 */

public class SearchPresenterFactory implements PresenterFactory<SearchPresenter> {
    @Override
    public SearchPresenter create() {
        return new SearchPresenter();
    }
}
