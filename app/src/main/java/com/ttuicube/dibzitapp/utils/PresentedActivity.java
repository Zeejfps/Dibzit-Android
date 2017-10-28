package com.ttuicube.dibzitapp.utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Zeejfps on 10/27/17.
 */

public abstract class PresentedActivity<T extends Presenter<V>, V>
        extends AppCompatActivity implements LoaderManager.LoaderCallbacks<T>{

    protected T presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportLoaderManager().initLoader(101, null, this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onViewAttached(getView());
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onViewDetached();
    }

    @Override
    public Loader<T> onCreateLoader(int id, Bundle args) {
        return new PresenterLoader<>(this, getPresenterFactory());
    }

    @Override
    public void onLoadFinished(Loader<T> loader, T presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onLoaderReset(Loader<T> loader) {
        this.presenter = null;
    }

    protected abstract PresenterFactory<T> getPresenterFactory();

    protected abstract V getView();
}
