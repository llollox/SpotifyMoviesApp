package com.lorenzorigato.movies.ui.favorites;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.FavoritesFragmentBinding;
import com.lorenzorigato.movies.ui.component.movielist.MovieAdapter;
import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;
import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class FavoritesFragment extends DaggerFragment implements MovieAdapter.Listener {


    // Static **************************************************************************************
    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    // Class attributes ****************************************************************************
    @Inject
    FavoritesViewModel viewModel;

    // Private class attributes ********************************************************************
    private FavoritesFragmentBinding binding;
    private MovieAdapter adapter;


    // Class methods *******************************************************************************
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = FavoritesFragmentBinding.inflate(inflater, container, false);
        this.binding.viewMovieListLayout.movieListEmptyPlaceHolderTextView.setText(R.string.favorites_empty_placehoder);
        this.configureRecyclerView(this.binding.viewMovieListLayout.movieListRecyclerView);
        this.viewModel.getStatus().observe(this, this::handleStatusChanged);
        this.viewModel.getState().observe(this, this::handleStateChanged);
        return this.binding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Activity activity = getActivity();
        if (activity != null) {
            activity.setTitle(R.string.favorites);
        }
    }


    // MovieAdapter.Listener methods ***************************************************************
    @Override
    public void onFavoriteButtonClicked(MovieViewHolder.Layout layout) {
        this.viewModel.onToggleFavorite(layout);
    }

    @Override
    public void onMovieClicked(MovieViewHolder.Layout layout) {
        Intent intent = MovieDetailActivity.getCallingIntent(getActivity(), layout.getId());
        startActivity(intent);
    }


    // Private class attributes ********************************************************************
    private void configureRecyclerView(RecyclerView recyclerView) {
        this.adapter = new MovieAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        recyclerView.setAdapter(this.adapter);
    }

    private void handleStateChanged(FavoritesView.State state) {
        this.binding.setState(state);
        this.adapter.submitList(state.getLayouts());
    }

    private void handleStatusChanged(FavoritesView.Status status) {
        switch (status) {

            case FAVORITE_NOT_SET_ERROR:
                Toast.makeText(getActivity(), R.string.favorites_error_unable_set_favorite, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
