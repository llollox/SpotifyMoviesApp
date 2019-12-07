package com.lorenzorigato.movies_sample;

import com.lorenzorigato.movies_sample.di.DaggerMoviesSampleApplicationComponent;
import com.lorenzorigato.movies_sample.di.MoviesSampleApplicationComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class MoviesSampleApplication extends DaggerApplication {

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        MoviesSampleApplicationComponent.Builder builder = DaggerMoviesSampleApplicationComponent.builder();
        builder.seedInstance(this);
        return builder
                .setApplication(this)
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
