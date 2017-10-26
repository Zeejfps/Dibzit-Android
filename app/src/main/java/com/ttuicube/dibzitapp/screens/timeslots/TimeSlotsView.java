package com.ttuicube.dibzitapp.screens.timeslots;

import com.ttuicube.dibzitapp.models.TimeSlot;

import java.util.List;

/**
 * Created by Zeejfps on 10/26/17.
 */

public interface TimeSlotsView {
    void displayNoSlots();
    void displayTimeSlots(List<TimeSlot> timeSlots);
    void setLoading(boolean loading);
}
