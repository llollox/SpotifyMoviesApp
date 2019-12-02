package com.lorenzorigato.movies.ui;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lorenzorigato.base.security.IAntiTampering;
import com.lorenzorigato.movies.BuildConfig;

import javax.inject.Inject;

import dagger.android.DaggerActivity;

public class MoviesActivity extends DaggerActivity {

    @Inject
    IAntiTampering antiTampering;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//         && BuildConfig.FLAVOR == "moonshotprod"
        if (!BuildConfig.DEBUG) {
            if (this.antiTampering.isAppTampered()) {
                this.finishAndRemoveTask();
                return;
            }
        }
    }
}
