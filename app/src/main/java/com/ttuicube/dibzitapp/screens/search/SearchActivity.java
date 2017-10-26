package com.ttuicube.dibzitapp.screens.search;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.screens.timeslots.TimeSlotsActivity;
import com.ttuicube.dibzitapp.utils.PresenterLoader;

import org.joda.time.DateTime;

/**
 * Created by zeejfps on 10/11/2017.
 */

public class SearchActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<SearchPresenter>,
                    SearchView, DatePickerDialog.OnDateSetListener {

    private static final int PRESENTER_ID = 101;

    private Button mSearchButton;
    private Button mChooseDateButton;
    private Spinner mDurationSpinner;

    private SearchPresenter mPresenter;

    private DatePickerDialog mDatePickerDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mSearchButton= (Button) findViewById(R.id.searchButton);
        mChooseDateButton = (Button) findViewById(R.id.dateButton);
        mDurationSpinner = (Spinner) findViewById(R.id.durationSpinner);

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.doSearchButtonClicked();
            }
        });

        mChooseDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPresenter.doChooseDateButtonClicked();
            }
        });

        mDurationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mPresenter.setReservationDuration(position+1);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                mPresenter.setReservationDuration(1);
            }
        });

        getSupportLoaderManager().initLoader(PRESENTER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onViewAttached(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onViewDetached();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDatePickerDialog != null) {
            mDatePickerDialog.dismiss();
        }
    }

    @Override
    public Loader<SearchPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(getApplicationContext(), new SearchPresenterFactory());
    }

    @Override
    public void onLoadFinished(Loader<SearchPresenter> loader, SearchPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void onLoaderReset(Loader loader) {
        mPresenter = null;
    }

    @Override
    public void updateDateTime(DateTime dateTime) {
        mChooseDateButton.setText(dateTime.toString("EE, MMM dd"));
    }

    @Override
    public void updateDuration(int duration) {
        mDurationSpinner.setSelection(duration - 1);
    }

    @Override
    public void showDatepickerDialog(DateTime date) {
        mDatePickerDialog = new DatePickerDialog(SearchActivity.this,
                SearchActivity.this, date.getYear(), date.getMonthOfYear()-1, date.getDayOfMonth());
        mDatePickerDialog.getDatePicker().setMinDate(DateTime.now().getMillis());
        mDatePickerDialog.show();
    }

    @Override
    public void startTimeslotsActivity(int duration, DateTime dateTime) {
        Intent intent = new Intent(SearchActivity.this, TimeSlotsActivity.class);
        intent.putExtra(TimeSlotsActivity.DURATION_EXTRA_KEY, duration);
        intent.putExtra(TimeSlotsActivity.DATE_EXTRA_KEY, dateTime);
        startActivity(intent);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        DateTime date = DateTime.now();
        if (date.getYear() == year && date.getMonthOfYear() == month+1 && date.getDayOfMonth() == day) {
            date = new DateTime(year, month+1, day, date.getHourOfDay(), 0);
        }
        else {
            date = new DateTime(year, month+1, day, 0, 0);
        }
        mPresenter.setReservationDateTime(date);
    }

}
