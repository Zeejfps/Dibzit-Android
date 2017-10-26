package com.ttuicube.dibzitapp.screens.search;

import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.utils.Presenter;

import org.joda.time.DateTime;

/**
 * Created by zeejfps on 10/26/17.
 */

public class SearchPresenter implements Presenter<SearchView> {

    private SearchView mView;
    private final Repository repo;

    public SearchPresenter(Repository repo) {
        this.repo = repo;
    }

    @Override
    public void onViewAttached(SearchView view) {
        mView = view;
        mView.updateDateTime(repo.getSearchDateTime());
        mView.updateDuration(repo.getReservationDuration());
    }

    public void doSearchButtonClicked() {
        mView.startTimeslotsActivity(repo.getReservationDuration(), repo.getSearchDateTime());
    }

    public void doChooseDateButtonClicked() {
        mView.showDatepickerDialog(repo.getSearchDateTime());
    }

    public void setReservationDuration(int duration) {
        repo.setReservationDuration(duration);
        mView.updateDuration(repo.getReservationDuration());
    }

    public void setReservationDateTime(DateTime dateTime) {
        repo.setSearchDateTime(dateTime);
        mView.updateDateTime(repo.getSearchDateTime());
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onDestroyed() {}

}
