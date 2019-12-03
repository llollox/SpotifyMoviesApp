package com.lorenzorigato.spotifymoviesapp.ui.navigator;

import android.content.Context;

import com.lorenzorigato.movies.ui.IMoviesNavigator;
import com.lorenzorigato.spotifymoviesapp.R;
import com.mikepenz.aboutlibraries.Libs;
import com.mikepenz.aboutlibraries.LibsBuilder;

public class MoviesNavigator implements IMoviesNavigator {

    @Override
    public void goToAbout(Context context) {
        new LibsBuilder()
                .withActivityTitle(context.getString(R.string.open_source_libraries))
                //provide a style (optional) (LIGHT, DARK, LIGHT_DARK_TOOLBAR)
                .withActivityStyle(Libs.ActivityStyle.LIGHT_DARK_TOOLBAR)
                //start the activity
                .start(context);
    }
}
