package com.ttuicube.dibzitapp.screens.timeslots;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.DibsRoomHours;
import com.ttuicube.dibzitapp.DibzitRepository;
import com.ttuicube.dibzitapp.models.TimeSlot;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class TimeSlotsLoader extends AsyncTaskLoader<List<TimeSlot>> {

    protected final DateTime date;
    protected final int duration;
    protected final DibzitRepository repo;

    public TimeSlotsLoader(Context context, DateTime date, int duration, DibzitRepository repo) {
        super(context);
        this.date = date;
        this.duration = duration;
        this.repo = repo;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<TimeSlot> loadInBackground() {
        List<TimeSlot> timeSlots = createTimeSlots(date, duration);

        List<DibsRoom> rooms = repo.fetchRooms();
        for (DibsRoom room : rooms) {

            List<DibsRoomHours> openHours = repo.fetchWorkingHours(date, room);
            List<DibsRoomHours> reservations = repo.fetchReservations(date, room);

            for (TimeSlot slot : timeSlots) {
                if (canBeAdded(slot, openHours, reservations)) {
                    slot.rooms.add(room);
                }
            }
        }

        List<TimeSlot> notEmptySlots = new ArrayList<>();
        for (TimeSlot slot : timeSlots) {
            if (slot.rooms.size() > 0) {
                notEmptySlots.add(slot);
            }
        }
        return notEmptySlots;
    }

    private List<TimeSlot> createTimeSlots(DateTime date, int duration) {
        List<TimeSlot> slots = new ArrayList<>();
        DateTime startTime = date;
        for (int i = startTime.getHourOfDay(); i < 24; i++) {
            DateTime endTime = startTime.plusHours(duration);
            slots.add(new TimeSlot(startTime, endTime));
            startTime = startTime.plusHours(1);
        }
        return slots;
    }

    private boolean canBeAdded(TimeSlot slot, List<DibsRoomHours> hours, List<DibsRoomHours> reservations) {
        boolean canBeAdded = false;
        for (DibsRoomHours h : hours) {
            if ((h.getStartTime().isBefore(slot.startTime) || h.getStartTime().isEqual(slot.startTime))
                    && (h.getEndTime().isAfter(slot.endTime) || h.getEndTime().isEqual(slot.endTime))) {
                canBeAdded = true;
            }
        }
        for (DibsRoomHours r : reservations) {
            if ((r.getStartTime().isBefore(slot.endTime) || r.getStartTime().isEqual(slot.endTime))
                    && (r.getEndTime().isAfter(slot.startTime) || r.getEndTime().isEqual(slot.startTime))) {
                canBeAdded = false;
            }
        }
        return canBeAdded;
    }

}
