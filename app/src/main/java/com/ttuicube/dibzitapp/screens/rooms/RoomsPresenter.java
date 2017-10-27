package com.ttuicube.dibzitapp.screens.rooms;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.TimeSlot;
import com.ttuicube.dibzitapp.repos.Repository;
import com.ttuicube.dibzitapp.screens.login.LoginActivity;
import com.ttuicube.dibzitapp.utils.Presenter;

/**
 * Created by Zeejfps on 10/27/17.
 */

public class RoomsPresenter implements Presenter<RoomsView> {

    private final Repository repo;
    private RoomsView view;

    public RoomsPresenter(Repository repo) {
        this.repo = repo;
    }

    @Override
    public void onViewAttached(RoomsView view) {
        this.view = view;
        TimeSlot timeSlot = repo.getSelectedTimeSlot();
        if (timeSlot != null) {
            view.updateTitle(timeSlot.startTime.toString("hh:00 aa") + " - " + timeSlot.endTime.toString("hh:00 aa"));
            view.displayRooms(timeSlot);
        }
    }

    @Override
    public void onViewDetached() {

    }

    @Override
    public void onDestroyed() {

    }

    public void onRoomSelected(DibsRoom room) {
        if (repo.getUserEmail().isEmpty()) {
            view.startLoginActivity();
        } else {
            TimeSlot timeSlot = repo.getSelectedTimeSlot();
            String title = room.name;
            String when = "When: " + timeSlot.startTime.toString("EE, MMM dd") + " @ " +
                timeSlot.startTime.toString("hh:00 aa") + " - " + timeSlot.endTime.toString("hh:00 aa");
            String email = "\n\nEmail: " + repo.getUserEmail();
            String phone = "\n\nPhone: (614)222-1337";
            view.displayDibsDialog(title, when, email, phone);
        }
    }

}
