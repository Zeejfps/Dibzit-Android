package com.ttuicube.dibzitapp.screens.timeslots;

import com.ttuicube.dibzitapp.repos.DibzitRepository;
import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.DibsRoomHours;
import com.ttuicube.dibzitapp.models.TimeSlot;
import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.utils.Presenter;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zeejfps on 10/26/17.
 */

public class TimeSlotsPresenter implements Presenter<TimeSlotsView> {

    private final Repository repo;

    private TimeSlotsView view;
    private List<TimeSlot> timeSlots = new ArrayList<>();

    public TimeSlotsPresenter(Repository repo) {
        this.repo = repo;
    }

    @Override
    public void onViewAttached(TimeSlotsView view) {
        this.view = view;
        notifyView();
    }

    @Override
    public void onViewDetached() {
        this.view = null;
    }

    @Override
    public void onDestroyed() {}

    public void loadTimeSlots() {
        view.setLoading(true);
        repo.fetchTimeSlots(repo.getSearchDateTime(), repo.getReservationDuration(), timeSlots -> {
           this.timeSlots = timeSlots;
           if (view != null) {
               notifyView();
               view.setLoading(false);
           }
       });
    }

    private void notifyView() {
        if (this.timeSlots.isEmpty())
            view.displayNoSlots();
        else
            view.displayTimeSlots(this.timeSlots);
    }

}
