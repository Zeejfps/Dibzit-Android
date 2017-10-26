package com.ttuicube.dibzitapp.screens.search;

import com.ttuicube.dibzitapp.models.TimeSlot;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by zeejfps on 10/26/17.
 */

public interface SearchView {

    void updateDateTime(DateTime dateTime);

    void updateDuration(int duration);

    void showDatePickerDialog(DateTime dateTime);

    void startTimeSlotsActivity(List<TimeSlot> timeSlotList);

    void displaySearchingDialog();

    void hideSearchingDialog();
}
