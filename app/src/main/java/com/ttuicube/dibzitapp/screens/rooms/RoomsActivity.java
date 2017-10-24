package com.ttuicube.dibzitapp.screens.rooms;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.models.DibsRoom;
import com.ttuicube.dibzitapp.models.TimeSlot;
import com.ttuicube.dibzitapp.screens.login.LoginActivity;

/**
 * Created by Zeejfps on 10/11/17.
 */

public class RoomsActivity extends AppCompatActivity implements RoomsAdapter.RoomSelectedCallback {

    public static final String TIME_SLOT_KEY = "TimeSlot";

    private TimeSlot timeSlot;
    private String email = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState != null) {
            timeSlot = savedInstanceState.getParcelable(TIME_SLOT_KEY);
        }
        else {
            timeSlot = getIntent().getParcelableExtra(TIME_SLOT_KEY);
        }

        email = PreferenceManager.getDefaultSharedPreferences(this)
                .getString(LoginActivity.EMAIL_KEY, "");

        setTitle(timeSlot.startTime.toString("hh:00 aa") + " - " + timeSlot.endTime.toString("hh:00 aa"));
        RoomsAdapter roomsAdapter = new RoomsAdapter(timeSlot.rooms, this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(roomsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(TIME_SLOT_KEY, timeSlot);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        timeSlot = savedInstanceState.getParcelable(TIME_SLOT_KEY);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("RoomsActivity", "onResult");
        if (requestCode == 1 && resultCode == 1) {
            email = data.getStringExtra(LoginActivity.EMAIL_KEY);
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRoomSelected(DibsRoom room) {
        if (email.isEmpty()) {
            startActivityForResult(new Intent(RoomsActivity.this, LoginActivity.class), 1);
        } else {
            new AlertDialog.Builder(this)
                .setTitle(room.name)
                .setMessage(
                        "\nWhen: " + timeSlot.startTime.toString("EE, MMM dd") +
                                " @ " + timeSlot.startTime.toString("hh:00 aa") + " - " + timeSlot.endTime.toString("hh:00 aa") +
                                "\n\nEmail: " + email +
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

}
