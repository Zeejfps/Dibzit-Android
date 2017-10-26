package com.ttuicube.dibzitapp.screens.search;

import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.utils.PresenterFactory;

/**
 * Created by zeejfps on 10/26/17.
 */

public class SearchPresenterFactory implements PresenterFactory<SearchPresenter> {

    private final Repository repo;

    public SearchPresenterFactory(Repository repo) {
        this.repo = repo;
    }

    @Override
    public SearchPresenter create() {
        return new SearchPresenter(repo);
    }
}
