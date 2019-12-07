package com.lorenzorigato.movies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;

import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.MovieDetailActivityBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

public class MovieDetailActivity extends DaggerAppCompatActivity {

    // Static **************************************************************************************
    private static final String ARG_MOVIE_ID = "ARG_MOVIE_ID";
    public static Intent getCallingIntent(Context context, int movieId) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(ARG_MOVIE_ID, movieId);
        return intent;
    }


    // Class attributes ****************************************************************************
    @Inject
    MovieDetailViewModel viewModel;


    // Private class attributes ********************************************************************
    private MovieDetailActivityBinding binding;


    // Class methods *******************************************************************************
    int getMovieId() {
        Bundle args = getIntent().getExtras();
        if (args != null) {
            return args.getInt(ARG_MOVIE_ID);
        }
        return -1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.movie_detail_activity);
        this.configureActionBar();
        this.viewModel.getState().observe(this, this.binding::setState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Private class methods ***********************************************************************
    private void configureActionBar() {
        this.setSupportActionBar(this.binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
