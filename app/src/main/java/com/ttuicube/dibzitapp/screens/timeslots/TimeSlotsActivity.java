package com.ttuicube.dibzitapp.screens.timeslots;

import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ttuicube.dibzitapp.DibzitApplication;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.models.TimeSlot;
import com.ttuicube.dibzitapp.utils.PresenterLoader;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TimeSlotsActivity extends AppCompatActivity
        implements TimeSlotsView, LoaderManager.LoaderCallbacks<TimeSlotsPresenter> {

    public static final String SLOTS_KEY = "Slots";

    private static final int PRESENTER_ID = 101;

    private TimeSlotsAdapter timeSlotsAdapter;

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView noRoomsLabel;

    private TimeSlotsPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        noRoomsLabel = (TextView) findViewById(R.id.noRoomsLabel);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipreRefreshLayout);

        swipeRefreshLayout.setOnRefreshListener(() -> presenter.loadTimeSlots());

        timeSlotsAdapter = new TimeSlotsAdapter(this, new ArrayList<>());
        recyclerView.setAdapter(timeSlotsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(PRESENTER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
        ArrayList<TimeSlot> timeSlots = getIntent().getParcelableArrayListExtra(SLOTS_KEY);
        if (timeSlots != null)
            presenter.setTimeSlots(timeSlots);
        else
            presenter.loadTimeSlots();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<TimeSlotsPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this,
                new TimeSlotsPresenterFactory(DibzitApplication.instance().getRepository()));
    }

    @Override
    public void onLoadFinished(Loader<TimeSlotsPresenter> loader, TimeSlotsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLoaderReset(Loader<TimeSlotsPresenter> loader) {
        this.presenter = null;
    }

    @Override
    public void displayNoSlots() {
        timeSlotsAdapter.setData(Collections.emptyList());
        recyclerView.setVisibility(View.GONE);
        noRoomsLabel.setVisibility(View.VISIBLE);
    }

    @Override
    public void displayTimeSlots(List<TimeSlot> timeSlots) {
        timeSlotsAdapter.setData(timeSlots);
        recyclerView.setVisibility(View.VISIBLE);
        noRoomsLabel.setVisibility(View.GONE);
    }

    @Override
    public void setLoading(boolean loading) {
        swipeRefreshLayout.setRefreshing(loading);
    }

    @Override
    public void updateTitle(String s) {
        setTitle(s);
    }
}
