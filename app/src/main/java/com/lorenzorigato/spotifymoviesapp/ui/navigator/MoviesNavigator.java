package com.lorenzorigato.spotifymoviesapp.ui.navigator;

import android.content.Context;
import android.widget.Toast;

import com.lorenzorigato.movies.ui.IMoviesNavigator;
import com.lorenzorigato.spotifymoviesapp.R;

public class MoviesNavigator implements IMoviesNavigator {

    @Override
    public void goToInformation(Context context) {
        Toast.makeText(context, context.getText(R.string.app_name), Toast.LENGTH_SHORT).show();
    }
}
