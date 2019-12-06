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
    public static Intent getCallingIntent(Context context) {
        return new Intent(context, MovieDetailActivity.class);
    }


    // Class attributes ****************************************************************************
    @Inject
    MovieDetailViewModel viewModel;


    // Private class attributes ********************************************************************
    private MovieDetailActivityBinding binding;


    // Class methods *******************************************************************************
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.binding = DataBindingUtil.setContentView(this, R.layout.movie_detail_activity);
        this.configureActionBar();
        this.binding.setLayout(this.viewModel.getLayout());
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
        this.binding.toolbar.setTitle("Batman Begins");
        this.setSupportActionBar(this.binding.toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
