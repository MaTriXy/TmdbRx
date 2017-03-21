package com.tmdb.android;

import android.app.Application;
import timber.log.Timber;

/**
 * Created by ronelg on 3/21/17.
 */

public class TmdbApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Timber.plant(new Timber.DebugTree());
    }
}
