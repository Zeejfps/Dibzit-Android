package com.ttuicube.dibzitapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zeejfps on 10/10/2017.
 */

public class TimeSlot implements Parcelable {

    public final DateTime startTime;
    public final DateTime endTime;
    public final List<DibsRoom> rooms;

    public TimeSlot(DateTime startTime, DateTime endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
        rooms = new ArrayList<>();
    }

    protected TimeSlot(Parcel in) {
        startTime = (DateTime)in.readSerializable();
        endTime = (DateTime)in.readSerializable();
        rooms = new ArrayList<>();
        in.readTypedList(rooms, DibsRoom.CREATOR);
    }

    public static final Creator<TimeSlot> CREATOR = new Creator<TimeSlot>() {
        @Override
        public TimeSlot createFromParcel(Parcel in) {
            return new TimeSlot(in);
        }

        @Override
        public TimeSlot[] newArray(int size) {
            return new TimeSlot[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(startTime);
        dest.writeSerializable(endTime);
        dest.writeTypedList(rooms);
    }
}
