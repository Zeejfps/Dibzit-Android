package com.ttuicube.dibzitapp;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.DibsRoomHours;
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Zeejfps on 10/12/17.
 */

public class DibzitRepository {

    public static final String API_URL = "http://tntech.evanced.info/dibsAPI/";

    private final Context context;
    private final DibsRestService service;

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
    }

    public List<DibsRoom> getDibsRooms() {
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

    public List<DibsRoomHours> getRoomHours(DateTime date, DibsRoom room) {
        if (!openHours.containsValue(date)) {
            try {
                Response<List<DibsRoomHours>> fetchHoursResponse = service
                        .fetchRoomHours(date.toString("yyyy-MM-dd"), room.roomID).execute();
                if (fetchHoursResponse.isSuccessful()) {
                    List<DibsRoomHours> hours = fetchHoursResponse.body();
                    if (hours != null) {
                        openHours.put(date, hours);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return openHours.get(date);
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
