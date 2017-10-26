package com.ttuicube.dibzitapp.repos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.database.DibzitDbHelper;
import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.DibsRoomHours;
import com.ttuicube.dibzitapp.models.TimeSlot;
import com.ttuicube.dibzitapp.network.DibsRestService;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ttuicube.dibzitapp.database.DibzitContract.WorkingHoursEntry;

/**
 * Created by Zeejfps on 10/12/17.
 */

public class DibzitRepository implements Repository {

    public static final String API_URL = "http://tntech.evanced.info/dibsAPI/";

    private final Context context;
    private final DibsRestService service;
    private final DibzitDbHelper dbHelper;

    private int reservationDuration;
    private DateTime searchDateTime;
    private List<DibsRoom> dibsRooms = new ArrayList<>();
    private Map<DateTime, List<DibsRoomHours>> openHours = new HashMap<>();

    public DibzitRepository(Context context) {
        this.context = context;

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DibsRoomHours.class, new DibsRoomHours.Serializer())
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.service = retrofit.create(DibsRestService.class);
        this.dbHelper = new DibzitDbHelper(context);

        searchDateTime = DateTime.now();
        reservationDuration = 1;
    }

    @Override
    public void fetchTimeSlots(DateTime date, int duration, Callback callback) {
        new Thread(() -> {
            List<TimeSlot> timeSlots = createTimeSlots(date, duration);

            List<DibsRoom> rooms = fetchRooms();
            for (DibsRoom room : rooms) {

                List<DibsRoomHours> openHours = fetchWorkingHours(date, room);
                List<DibsRoomHours> reservations = fetchReservations(date, room);

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

            new Handler(context.getMainLooper()).post(() -> {
                callback.onSuccess(notEmptySlots);
            });
        }).start();
    }

    @Override
    public void setSearchDateTime(DateTime dateTime) {
        this.searchDateTime = dateTime;
    }

    @Override
    public DateTime getSearchDateTime() {
        return searchDateTime;
    }

    @Override
    public void setReservationDuration(int duration) {
        this.reservationDuration = duration;
    }

    @Override
    public int getReservationDuration() {
        return reservationDuration;
    }

    private List<DibsRoom> fetchRooms() {
        if (dibsRooms.isEmpty()) {
            InputStream in = context.getResources().openRawResource(R.raw.rooms);
            Writer writer = new StringWriter();
            char[] buffer = new char[1024];
            try {
                Reader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
                int n;
                while ((n = reader.read(buffer)) != -1) {
                    writer.write(buffer, 0, n);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try { in.close();} catch (IOException e) {}
            }
            String json = writer.toString();
            DibsRoom[] rooms = new Gson().fromJson(json, DibsRoom[].class);
            dibsRooms = Arrays.asList(rooms);
        }
        return dibsRooms;
    }

    private List<DibsRoomHours> fetchWorkingHours(DateTime date, DibsRoom room) {
        if (!openHours.containsKey(date)) {
            List<DibsRoomHours> workingHours = fetchWorkingHoursFromDatabase(date, room);
            if (workingHours.isEmpty()) {
                workingHours = fetchWorkingHoursFromNetwork(date, room);
                saveWorkingHoursToDatabase(date, room, workingHours);
            }
            openHours.put(date, workingHours);
        }
        return openHours.get(date);
    }

    private List<DibsRoomHours> fetchReservations(DateTime date, DibsRoom room) {
        try {
            Response<List<DibsRoomHours>> fetchReservationsResponse = service
                    .fetchReservations(date.toString("yyyy-MM-dd"), room.roomID).execute();
            if (fetchReservationsResponse.isSuccessful()) {
                List<DibsRoomHours> reservations = fetchReservationsResponse.body();
                if (reservations != null) {
                    return reservations;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private List<DibsRoomHours> fetchWorkingHoursFromDatabase(DateTime date, DibsRoom room) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
            WorkingHoursEntry.COLUMN_ROOM_ID,
            WorkingHoursEntry.COLUMN_START_TIME,
            WorkingHoursEntry.COLUMN_END_TIME
        };

        String selection = WorkingHoursEntry.COLUMN_ROOM_ID + " = ? AND "
            + WorkingHoursEntry.COLUMN_DATE + " = ?";

        String[] args = {
            Integer.toString(room.roomID),
            date.toString("yyyy-MM-dd")
        };

        Cursor cursor = db.query(WorkingHoursEntry.TABLE_NAME,
                projection, selection, args, null, null, null);

        List<DibsRoomHours> workingHours = new ArrayList<>();
        while (cursor.moveToNext()) {
            int roomID = cursor.getInt(cursor.getColumnIndex(WorkingHoursEntry.COLUMN_ROOM_ID));
            String startTimeStr = cursor.getString(cursor.getColumnIndex(WorkingHoursEntry.COLUMN_START_TIME));
            String endTimeStr = cursor.getString(cursor.getColumnIndex(WorkingHoursEntry.COLUMN_END_TIME));
            DateTime startTime = DateTime.parse(startTimeStr);
            DateTime endTime = DateTime.parse(endTimeStr);
            workingHours.add(new DibsRoomHours(roomID, startTime, endTime));
        }

        db.close();
        return workingHours;
    }

    private List<DibsRoomHours> fetchWorkingHoursFromNetwork(DateTime date, DibsRoom room) {
        try {
            Response<List<DibsRoomHours>> fetchHoursResponse = service
                    .fetchRoomHours(date.toString("yyyy-MM-dd"), room.roomID).execute();
            if (fetchHoursResponse.isSuccessful()) {
                List<DibsRoomHours> workingHours = fetchHoursResponse.body();
                if (workingHours != null) {
                    return workingHours;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private void saveWorkingHoursToDatabase(DateTime date, DibsRoom room, List<DibsRoomHours> workingHours) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        for (DibsRoomHours hours : workingHours) {
            ContentValues values = new ContentValues();
            values.put(WorkingHoursEntry.COLUMN_DATE, date.toString("yyyy-MM-dd"));
            values.put(WorkingHoursEntry.COLUMN_ROOM_ID, room.roomID);
            values.put(WorkingHoursEntry.COLUMN_START_TIME, hours.getStartTime().toString());
            values.put(WorkingHoursEntry.COLUMN_END_TIME, hours.getEndTime().toString());

            db.insert(WorkingHoursEntry.TABLE_NAME, null, values);
        }
        db.close();
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
