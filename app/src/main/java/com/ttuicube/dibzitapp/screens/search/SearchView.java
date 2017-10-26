package com.ttuicube.dibzitapp.screens.search;

import org.joda.time.DateTime;

/**
 * Created by zeejfps on 10/26/17.
 */

public interface SearchView {

    void updateDateTime(DateTime dateTime);

    void updateDuration(int duration);

    void showDatepickerDialog(DateTime dateTime);

    void startTimeslotsActivity(int duration, DateTime dateTime);
}
