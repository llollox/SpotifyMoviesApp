package com.lorenzorigato.movies.ui.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.lorenzorigato.base.security.ISecurityManager;
import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.MovieDetailActivityBinding;
import com.lorenzorigato.movies.ui.detail.actors.ActorAdapter;

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

    @Inject
    ISecurityManager securityManager;


    // Private class attributes ********************************************************************
    private MovieDetailActivityBinding binding;
    private ActorAdapter actorAdapter;


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

        if (!this.securityManager.canAppExecute()) {
            this.finishAndRemoveTask();
            return;
        }

        this.binding = DataBindingUtil.setContentView(this, R.layout.movie_detail_activity);
        this.configureActionBar();
        this.configureActorRecyclerView();
        this.viewModel.getState().observe(this, this::handleStateChanged);
        this.viewModel.getStatus().observe(this, this::handleStatusChanged);
        this.binding.fab.setOnClickListener(v -> this.viewModel.onToggleFavorite());
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

    private void configureActorRecyclerView() {
        this.actorAdapter = new ActorAdapter();
        RecyclerView recyclerView = this.binding.movieDetailActorsRecyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        recyclerView.setAdapter(this.actorAdapter);
    }

    private void handleStateChanged(MovieDetailView.State state) {
        this.binding.setState(state);
        this.actorAdapter.submitList(state.getActors());
    }

    private void handleStatusChanged(MovieDetailView.Status status) {
        switch (status) {

            case FAVORITE_ADD_SUCCESS:
                Snackbar.make(this.binding.getRoot(), R.string.favorites_movie_added_success, Snackbar.LENGTH_SHORT).show();
                break;

            case FAVORITE_REMOVED_SUCCESS:
                Snackbar.make(this.binding.getRoot(), R.string.favorites_movie_removed_success, Snackbar.LENGTH_SHORT).show();
                break;

            case FAVORITE_NOT_SET_ERROR:
                Toast.makeText(this, R.string.favorites_error_unable_set_favorite, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
