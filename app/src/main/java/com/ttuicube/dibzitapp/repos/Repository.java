package com.ttuicube.dibzitapp.repos;

import com.ttuicube.dibzitapp.models.TimeSlot;

import org.joda.time.DateTime;

import java.util.List;

/**
 * Created by Zeejfps on 10/26/17.
 */

public interface Repository {

    interface Callback {
        void onSuccess(List<TimeSlot> timeSlots);
    }

    void fetchTimeSlots(DateTime date, int duration, Callback callback);

    void setSearchDateTime(DateTime dateTime);

    DateTime getSearchDateTime();

    void setReservationDuration(int duration);

    int getReservationDuration();

    void setUserEmail(String email);

    String getUserEmail();

    void setSelectedTimeSlot(TimeSlot timeSlot);

    TimeSlot getSelectedTimeSlot();
}
