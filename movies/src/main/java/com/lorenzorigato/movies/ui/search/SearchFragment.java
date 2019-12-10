package com.lorenzorigato.movies.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.lorenzorigato.base.network.NetworkState;
import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.SearchFragmentBinding;
import com.lorenzorigato.movies.ui.component.movielist.MovieAdapter;
import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;
import com.lorenzorigato.movies.ui.detail.MovieDetailActivity;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;

public class SearchFragment extends DaggerFragment implements MovieAdapter.Listener{


    // Class attributes ****************************************************************************
    @Inject
    SearchViewModel viewModel;
    private SearchFragmentBinding binding;


    // Class methods *******************************************************************************
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_actions, menu);
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchMenuItem.getActionView();
        this.setupSearchView(searchView, searchMenuItem);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.binding = SearchFragmentBinding.inflate(inflater, container, false);

        MovieAdapter adapter = new MovieAdapter(this);
        RecyclerView recyclerView = this.binding.viewMovieListLayout.movieListRecyclerView;
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (adapter.hasExtraRow() && position == adapter.getItemCount() - 1) {
                    return 2;
                }
                else {
                    return 1;
                }
            }
        });

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        this.viewModel.getLayouts().observe(this, layouts -> {
            boolean isRecyclerViewVisible = layouts != null && !layouts.isEmpty();
            this.setRecyclerViewVisible(isRecyclerViewVisible);
            this.setEmptyPlaceholderTextViewVisible(!isRecyclerViewVisible);
            adapter.submitList(layouts);
        });

        this.viewModel.getState().observe(this, this::handleStateChanged);
        this.viewModel.getMoviesNetworkState().observe(this, this::handleMovieNetworkStateChanged);

        if (savedInstanceState == null) {
            this.viewModel.onViewCreated();
        }

        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        this.viewModel.getStatus().observe(this, this::handleStatusChanged);
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(this.viewModel.getGenreName().getValue());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        this.binding.viewMovieListLayout.movieListRecyclerView.setAdapter(null);
        this.binding = null;
        this.viewModel = null;
    }

    // Private class methods ***********************************************************************
    private void handleStatusChanged(com.lorenzorigato.movies.ui.search.SearchView.Status status) {
        switch (status) {
            case GENRES_NOT_LOADED_ERROR:
                Toast.makeText(getActivity(), R.string.search_error_genres_not_loaded, Toast.LENGTH_SHORT).show();
                break;

            case INVALID_GENRE_ERROR:
                Toast.makeText(getActivity(), R.string.search_invalid_genre_error, Toast.LENGTH_SHORT).show();
                break;

            case FAVORITE_ADD_SUCCESS:
                Snackbar.make(this.binding.getRoot(), R.string.favorites_movie_added_success, Snackbar.LENGTH_SHORT).show();
                break;

            case FAVORITE_REMOVED_SUCCESS:
                Snackbar.make(this.binding.getRoot(), R.string.favorites_movie_removed_success, Snackbar.LENGTH_SHORT).show();
                break;

            case FAVORITE_NOT_SET_ERROR:
                Toast.makeText(getActivity(), R.string.favorites_error_unable_set_favorite, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void handleMovieNetworkStateChanged(NetworkState networkState) {
        switch (networkState) {
            case ERROR:
                this.setLoadingVisible(false);
                this.setRecyclerViewLoadingVisible(false);
                Toast.makeText(getActivity(), R.string.search_error_movies_not_loaded, Toast.LENGTH_SHORT).show();
                break;

            case LOADING:
                RecyclerView.Adapter adapter = this.binding.viewMovieListLayout.movieListRecyclerView.getAdapter();
                if (adapter == null) {
                    return;
                }

                if (adapter.getItemCount() == 0) {
                    this.setLoadingVisible(true);
                }
                else {
                    this.setRecyclerViewLoadingVisible(true);
                }
                break;

            case SUCCESS:
                this.setRecyclerViewLoadingVisible(false);
                this.setLoadingVisible(false);
                break;
        }
    }

    private void handleStateChanged(com.lorenzorigato.movies.ui.search.SearchView.State state) {
        this.binding.setState(state);
    }

    private void setupSearchView(SearchView searchView, MenuItem searchMenuItem) {
        SearchViewCursorAdapter adapter = new SearchViewCursorAdapter(getActivity());
        searchView.setSuggestionsAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                SearchFragment.this.viewModel.onQuerySubmitted(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                SearchFragment.this.viewModel.onQueryChanged(string);
                return false;
            }
        });

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                SearchFragment.this.viewModel.onSuggestionClicked(position);
                searchMenuItem.collapseActionView();
                return false;
            }
        });

        this.viewModel.getGenreName().observe(this, SearchFragment.this::setTitle);
        this.viewModel.getSuggestions().observe(this, adapter::setSuggestions);
    }

    private void setTitle(String query) {
        String title = query == null ? getString(R.string.search) : query;
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }


    // MovieAdapter.Listener methods ***************************************************************
    @Override
    public void onFavoriteButtonClicked(MovieViewHolder.Layout layout) {
        this.viewModel.onToggleFavorite(layout.getId());
    }

    @Override
    public void onMovieClicked(MovieViewHolder.Layout layout) {
        Intent intent = MovieDetailActivity.getCallingIntent(getActivity(), layout.getId());
        startActivity(intent);
    }

    private void setRecyclerViewVisible(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        this.binding.viewMovieListLayout.movieListRecyclerView.setVisibility(visibility);
    }

    private void setEmptyPlaceholderTextViewVisible(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        this.binding.viewMovieListLayout.movieListEmptyPlaceHolderTextView.setVisibility(visibility);
    }

    private void setLoadingVisible(boolean isVisible) {
        int visibility = isVisible ? View.VISIBLE : View.GONE;
        this.binding.viewMovieListLayout.movieListLoadingContainerFrameLayout.setVisibility(visibility);
    }

    private void setRecyclerViewLoadingVisible(boolean isLoadingVisible) {
        RecyclerView.Adapter adapter = this.binding.viewMovieListLayout.movieListRecyclerView.getAdapter();
        if (adapter instanceof MovieAdapter) {
            MovieAdapter movieAdapter = (MovieAdapter) adapter;
            movieAdapter.showLoading(isLoadingVisible);
        }
    }
}
