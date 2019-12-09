package com.lorenzorigato.movies;

import com.lorenzorigato.movies.di.DaggerMoviesAppComponent;
import com.lorenzorigato.movies.di.MoviesAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class MoviesApplication extends DaggerApplication {


    // Private class attributes ********************************************************************
    private MoviesAppComponent component;


    // Class methods *******************************************************************************
    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        MoviesAppComponent.Builder builder = DaggerMoviesAppComponent.builder();
        builder.seedInstance(this);
        this.component = builder.setApplication(this).build();
        return this.component;
    }

    public MoviesAppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
