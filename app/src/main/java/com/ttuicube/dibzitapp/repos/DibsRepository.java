package com.ttuicube.dibzitapp.repos;

import android.content.Context;
import android.content.res.Resources;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.model.DibsRoom;
import com.ttuicube.dibzitapp.model.DibsRoomHours;
import com.ttuicube.dibzitapp.model.TimeSlot;
import com.ttuicube.dibzitapp.rest.DibsRestService;

import org.joda.time.DateTime;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import retrofit2.Response;

/**
 * Created by Zeejfps on 10/12/17.
 */

public class DibsRepository {

    private final Context context;
    private final DibsRestService service;

    public DibsRepository(Context context, DibsRestService service) {
        this.context = context;
        this.service = service;
    }

    public List<DibsRoom> getDibsRooms() {
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
        return Arrays.asList(rooms);
    }

    public List<DibsRoomHours> getRoomHours(DateTime date, DibsRoom room) {
        try {
            Response<List<DibsRoomHours>> fetchHoursResponse = service
                    .fetchRoomHours(date.toString("yyyy-MM-dd"), room.roomID).execute();
            if (fetchHoursResponse.isSuccessful()) {
                List<DibsRoomHours> openHours = fetchHoursResponse.body();
                if (openHours != null) {
                    return openHours;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    public List<DibsRoomHours> getReservations(DateTime date, DibsRoom room) {
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

}
