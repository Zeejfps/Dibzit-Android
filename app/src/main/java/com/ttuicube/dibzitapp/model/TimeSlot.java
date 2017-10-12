package com.ttuicube.dibzitapp.model;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class TimeSlot {

    public final DateTime startTime;
    public final DateTime endTime;
    public final List<DibsRoom> rooms;

    public TimeSlot(DateTime startTime, DateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        rooms = new ArrayList<>();
    }

}
