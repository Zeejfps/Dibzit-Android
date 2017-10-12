package com.ttuicube.dibzitapp.modules.rooms;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.model.DibsRoom;
import com.ttuicube.dibzitapp.model.TimeSlot;
import com.ttuicube.dibzitapp.modules.timeslots.TimeSlotsAdapter;

import java.util.ArrayList;

/**
 * Created by Zeejfps on 10/11/17.
 */

public class RoomsActivity extends AppCompatActivity implements RoomsAdapter.RoomSelectedCallback {

    public static final String TIME_SLOT_KEY = "TimeSlot";

    private TimeSlot timeSlot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        timeSlot = getIntent().getParcelableExtra(TIME_SLOT_KEY);
        setTitle(timeSlot.startTime.toString("hh:00 aa") + " - " + timeSlot.endTime.toString("hh:00 aa"));
        RoomsAdapter roomsAdapter = new RoomsAdapter(timeSlot.rooms, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(roomsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onRoomSelected(DibsRoom room) {
        new AlertDialog.Builder(this)
            .setTitle(room.name)
            .setMessage(
                "\nWhen: " + timeSlot.startTime.toString("EE, MMM dd") +
                " @ " + timeSlot.startTime.toString("hh:00 aa") + " - " + timeSlot.endTime.toString("hh:00 aa") +
                "\n\nEmail: ensdadddv42@students.tntech.edu" +
                "\n\nPhone: (614)222-1337")
            .setPositiveButton("Dibzit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            })
            .show();
    }

}
