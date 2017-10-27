package com.ttuicube.dibzitapp.screens.rooms;

import com.ttuicube.dibzitapp.models.TimeSlot;

/**
 * Created by Zeejfps on 10/27/17.
 */

public interface RoomsView {

    void updateTitle(String title);

    void displayRooms(TimeSlot timeSlot);

    void startLoginActivity();

    void displayDibsDialog(String title, String when, String email, String phone);

}
