package com.vggbudge.educhat;

import android.app.Application;

import timber.log.Timber;

public class NotepadApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
