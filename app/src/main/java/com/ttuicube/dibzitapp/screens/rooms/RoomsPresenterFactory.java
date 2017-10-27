package com.ttuicube.dibzitapp.screens.rooms;

import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.utils.PresenterFactory;

/**
 * Created by Zeejfps on 10/27/17.
 */

public class RoomsPresenterFactory implements PresenterFactory<RoomsPresenter> {

    private final Repository repo;

    public RoomsPresenterFactory(Repository repo) {
        this.repo = repo;
    }

    @Override
    public RoomsPresenter create() {
        return new RoomsPresenter(repo);
    }

}
