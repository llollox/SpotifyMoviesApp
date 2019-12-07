package com.lorenzorigato.movies_sample.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;
import com.lorenzorigato.movies.ui.favorites.FavoritesFragment;
import com.lorenzorigato.movies_sample.R;

import dagger.android.support.DaggerAppCompatActivity;

public class TestActivity extends DaggerAppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.startFavoritesFragment();
    }

    private void startFavoritesFragment() {
        this.setContentView(R.layout.test_activity);
        FavoritesFragment fragment = FavoritesFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame_layout, fragment)
                .commit();
    }

    private void startDetailActivity() {
        Intent intent = MovieDetailActivity.getCallingIntent(this, 682);
        startActivity(intent);
        finish();
    }
}
