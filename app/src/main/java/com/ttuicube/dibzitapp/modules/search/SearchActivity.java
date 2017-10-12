package com.ttuicube.dibzitapp.modules.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.modules.timeslots.TimeSlotsActivity;

import org.joda.time.DateTime;

/**
 * Created by zeejfps on 10/11/2017.
 */

public class SearchActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    protected Button dateButton;

    private int duration;
    private DateTime date;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        if (savedInstanceState != null) {
            duration = savedInstanceState.getInt(TimeSlotsActivity.DURATION_EXTRA_KEY);
            date = (DateTime)savedInstanceState.getSerializable(TimeSlotsActivity.DATE_EXTRA_KEY);
        }
        else {
            date = DateTime.now();
            duration = 1;
        }

        dateButton = (Button) findViewById(R.id.dateButton);
        dateButton.setText(date.toString("EE, MMM dd"));
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(SearchActivity.this,
                        SearchActivity.this, date.getYear(), date.getMonthOfYear()-1, date.getDayOfMonth());
                dialog.getDatePicker().setMinDate(DateTime.now().getMillis());
                dialog.getDatePicker().setMaxDate(DateTime.now().plusDays(14).getMillis());
                dialog.show();
            }
        });

        Spinner durationSpinner = (Spinner) findViewById(R.id.durationSpinner);
        durationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                duration = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                duration = 0;
            }
        });

        Button button = (Button) findViewById(R.id.searchButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SearchActivity.this, TimeSlotsActivity.class);
                intent.putExtra(TimeSlotsActivity.DURATION_EXTRA_KEY, duration);
                intent.putExtra(TimeSlotsActivity.DATE_EXTRA_KEY, date);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        DateTime now = DateTime.now();
        if (now.getYear() == year && now.getMonthOfYear() == month+1 && now.getDayOfMonth() == dayOfMonth) {
            date = new DateTime(year, month+1, dayOfMonth, now.getHourOfDay(), 0);
        }
        else {
            date = new DateTime(year, month+1, dayOfMonth, 0, 0);
        }
        dateButton.setText(date.toString("EE, MMM dd"));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(TimeSlotsActivity.DATE_EXTRA_KEY, date);
        outState.putInt(TimeSlotsActivity.DURATION_EXTRA_KEY, duration);
    }
}
