package com.ttuicube.dibzitapp.screens.timeslots;

import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.utils.PresenterFactory;

/**
 * Created by Zeejfps on 10/26/17.
 */

public class TimeSlotsPresenterFactory implements PresenterFactory<TimeSlotsPresenter> {

    private final Repository repo;

    public TimeSlotsPresenterFactory(Repository repo) {
        this.repo = repo;
    }


    @Override
    public TimeSlotsPresenter create() {
        return new TimeSlotsPresenter(repo);
    }
}
