package com.ttuicube.dibzitapp.screens.search;

import com.ttuicube.dibzitapp.utils.Presenter;

import org.joda.time.DateTime;

/**
 * Created by zeejfps on 10/26/17.
 */

public class SearchPresenter implements Presenter<SearchView> {

    private int mReservationDuration;
    private DateTime mReservationDateTime;

    private SearchView mView;

    public SearchPresenter() {
        mReservationDuration = 1;
        mReservationDateTime = DateTime.now();
    }

    @Override
    public void onViewAttached(SearchView view) {
        mView = view;
        mView.updateDateTime(mReservationDateTime);
        mView.updateDuration(mReservationDuration);
    }

    public void doSearchButtonClicked() {
        mView.startTimeslotsActivity(mReservationDuration, mReservationDateTime);
    }

    public void doChooseDateButtonClicked() {
        mView.showDatepickerDialog(mReservationDateTime);
    }

    public void setReservationDuration(int duration) {
        this.mReservationDuration = duration;
        mView.updateDuration(mReservationDuration);
    }

    public void setReservationDateTime(DateTime dateTime) {
        this.mReservationDateTime = dateTime;
        mView.updateDateTime(mReservationDateTime);
    }

    @Override
    public void onViewDetached() {
        mView = null;
    }

    @Override
    public void onDestroyed() {

    }

}
