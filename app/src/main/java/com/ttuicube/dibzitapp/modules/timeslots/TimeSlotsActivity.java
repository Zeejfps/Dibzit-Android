package com.ttuicube.dibzitapp.modules.timeslots;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.model.DibsRoomHours;
import com.ttuicube.dibzitapp.repos.DibsRepository;
import com.ttuicube.dibzitapp.rest.DibsRestService;
import com.ttuicube.dibzitapp.model.TimeSlot;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TimeSlotsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<TimeSlot>> {

    public static final String API_URL = "http://tntech.evanced.info/dibsAPI/";

    public static final String DATE_EXTRA_KEY = "Date";
    public static final String DURATION_EXTRA_KEY = "Duration";

    protected static final int TIME_SLOTS_LOADER_ID = 1;

    protected TimeSlotsAdapter timeSlotsAdapter;

    protected ProgressBar progressBar;

    protected DibsRestService service;
    protected DibsRepository repo;

    private DateTime date;
    private int duration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slots);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (savedInstanceState != null) {
            date = (DateTime) savedInstanceState.getSerializable(DATE_EXTRA_KEY);
            duration = savedInstanceState.getInt(DURATION_EXTRA_KEY);
        }
        else {
            date = (DateTime) getIntent().getSerializableExtra(DATE_EXTRA_KEY);
            duration = getIntent().getIntExtra(DURATION_EXTRA_KEY, 1);
        }

        setTitle(date.toString("EE, MMM dd"));

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DibsRoomHours.class, new DibsRoomHours.Serializer())
                .create();

        OkHttpClient client = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        service = retrofit.create(DibsRestService.class);
        repo = new DibsRepository(this, service);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        timeSlotsAdapter = new TimeSlotsAdapter(this, new ArrayList<TimeSlot>());
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(timeSlotsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(TIME_SLOTS_LOADER_ID, null, this);
    }



    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DATE_EXTRA_KEY, date);
        outState.putInt(DURATION_EXTRA_KEY, duration);
    }

    @Override
    public Loader<List<TimeSlot>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        return new TimeSlotsLoader(this, date, duration, repo);
    }

    @Override
    public void onLoadFinished(Loader<List<TimeSlot>> loader, List<TimeSlot> data) {
        Log.d("TEST", "onLoadFinished()");
        timeSlotsAdapter.setData(data);
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<List<TimeSlot>> loader) {
        progressBar.setVisibility(View.VISIBLE);
    }

}
