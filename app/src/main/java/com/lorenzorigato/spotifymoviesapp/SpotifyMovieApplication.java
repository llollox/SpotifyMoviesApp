package com.lorenzorigato.spotifymoviesapp;

import com.lorenzorigato.base.security.IAntiTampering;
import com.lorenzorigato.spotifymoviesapp.di.ApplicationComponent;
import com.lorenzorigato.spotifymoviesapp.di.DaggerApplicationComponent;
import com.lorenzorigato.spotifymoviesapp.util.CrashReportingTree;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class SpotifyMovieApplication extends DaggerApplication {

    @Inject
    IAntiTampering antiTampering;

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        ApplicationComponent.Builder builder = DaggerApplicationComponent.builder();
        builder.seedInstance(this);
        return builder
                .setApplication(this)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (!BuildConfig.DEBUG && BuildConfig.FLAVOR.equals("prod")) {
            if (this.antiTampering.isAppTampered()) {
                throw new RuntimeException();
            }
        }

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        else {
            Timber.plant(new CrashReportingTree());
        }
    }
}
