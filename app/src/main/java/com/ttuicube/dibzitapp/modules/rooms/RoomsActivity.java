package com.ttuicube.dibzitapp.modules.rooms;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.model.TimeSlot;
import com.ttuicube.dibzitapp.modules.timeslots.TimeSlotsAdapter;

import java.util.ArrayList;

/**
 * Created by Zeejfps on 10/11/17.
 */

public class RoomsActivity extends AppCompatActivity {

    public static final String TIME_SLOT_KEY = "TimeSlot";

    private TimeSlot timeSlot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        timeSlot = (TimeSlot) getIntent().getParcelableExtra(TIME_SLOT_KEY);
        RoomsAdapter roomsAdapter = new RoomsAdapter(timeSlot.rooms);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(roomsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
