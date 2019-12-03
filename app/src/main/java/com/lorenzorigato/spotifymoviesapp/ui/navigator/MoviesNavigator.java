package com.lorenzorigato.spotifymoviesapp.ui.navigator;

import android.content.Context;
import android.content.Intent;

import com.lorenzorigato.movies.ui.IMoviesNavigator;
import com.lorenzorigato.settings.SettingsActivity;

public class MoviesNavigator implements IMoviesNavigator {

    @Override
    public void goToSettings(Context context) {
        Intent intent = SettingsActivity.getCallingIntent(context);
        context.startActivity(intent);
    }
}
