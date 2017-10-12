package com.ttuicube.dibzitapp.modules.timeslots;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.ttuicube.dibzitapp.model.DibsRoom;
import com.ttuicube.dibzitapp.model.DibsRoomHours;
import com.ttuicube.dibzitapp.repos.DibsRepository;
import com.ttuicube.dibzitapp.model.TimeSlot;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class TimeSlotsLoader extends AsyncTaskLoader<List<TimeSlot>> {

    protected final DateTime date;
    protected final int duration;
    protected final DibsRepository repo;

    public TimeSlotsLoader(Context context, DateTime date, int duration, DibsRepository repo) {
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

        List<DibsRoom> rooms = repo.getDibsRooms();
        for (DibsRoom room : rooms) {

            List<DibsRoomHours> openHours = repo.getRoomHours(date, room);
            List<DibsRoomHours> reservations = repo.getReservations(date, room);
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

    private boolean canBeAdded(TimeSlot slot, List<DibsRoomHours> hours, List<DibsRoomHours> resevations) {
        boolean canBeAdded = false;
        for (DibsRoomHours h : hours) {
            if ((h.startTime.isBefore(slot.startTime) || h.startTime.isEqual(slot.startTime))
                    && (h.endTime.isAfter(slot.endTime) || h.endTime.isEqual(slot.endTime))) {
                canBeAdded = true;
            }
        }
        for (DibsRoomHours r : resevations) {
            if ((r.startTime.isBefore(slot.endTime) || r.startTime.isEqual(slot.endTime))
                    && (r.endTime.isAfter(slot.startTime) || r.endTime.isEqual(slot.startTime))) {
                canBeAdded = false;
            }
        }
        return canBeAdded;
    }

}
