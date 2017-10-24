package com.ttuicube.dibzitapp.network;

import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.DibsRoomHours;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by zeejfps on 10/11/2017.
 */

public interface DibsRestService  {

    @GET("rooms/")
    Call<List<DibsRoom>> fetchDibsRooms();

    @GET("roomHours/{date}/{roomID}")
    Call<List<DibsRoomHours>> fetchRoomHours(@Path("date") String date, @Path("roomID") int roomID);

    @GET("reservations/{date}/{roomID}")
    Call<List<DibsRoomHours>> fetchReservations(@Path("date") String date, @Path("roomID") int roomID);

}
