package com.ttuicube.dibzitapp.screens.rooms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.NavUtils;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ttuicube.dibzitapp.DibzitApplication;
import com.ttuicube.dibzitapp.R;
import com.ttuicube.dibzitapp.models.TimeSlot;
import com.ttuicube.dibzitapp.screens.login.LoginActivity;
import com.ttuicube.dibzitapp.utils.PresenterLoader;

import java.util.ArrayList;

/**
 * Created by Zeejfps on 10/11/17.
 */

public class RoomsActivity extends AppCompatActivity implements
            RoomsView, LoaderManager.LoaderCallbacks<RoomsPresenter> {

    private static final int PRESENTER_ID = 101;

    private RoomsAdapter roomsAdapter;
    private RecyclerView recyclerView;

    private RoomsPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        roomsAdapter = new RoomsAdapter(new ArrayList<>(), room -> {
            presenter.onRoomSelected(room);
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setAdapter(roomsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        getSupportLoaderManager().initLoader(PRESENTER_ID, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewAttached(this);
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
            case R.id.action_logout:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public Loader<RoomsPresenter> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this,
                new RoomsPresenterFactory(DibzitApplication.instance().getRepository()));
    }

    @Override
    public void onLoadFinished(Loader<RoomsPresenter> loader, RoomsPresenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLoaderReset(Loader<RoomsPresenter> loader) {
        this.presenter = null;
    }

    @Override
    public void updateTitle(String title) {
        setTitle(title);
    }

    @Override
    public void displayRooms(TimeSlot timeSlot) {
        roomsAdapter.setData(timeSlot.rooms);
    }

    @Override
    public void startLoginActivity() {
        startActivity(new Intent(RoomsActivity.this, LoginActivity.class));
    }

    @Override
    public void displayDibsDialog(String title, String when, String email, String phone) {
        new AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(
                when +
                email +
                phone
            )
            .setPositiveButton("Dibzit", (dialog, which) -> {

            })
            .setNegativeButton("Cancel", (dialog, which) -> {

            })
            .show();
    }
}
