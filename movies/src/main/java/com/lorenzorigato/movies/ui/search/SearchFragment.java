package com.lorenzorigato.movies.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.SearchFragmentBinding;

import javax.inject.Inject;

import dagger.android.support.DaggerFragment;
import timber.log.Timber;

public class SearchFragment extends DaggerFragment {

    @Inject
    SearchViewModel searchViewModel;

    private String query = null;

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

        SearchFragmentBinding binding = SearchFragmentBinding.inflate(inflater, container, false);

        binding.favoritesBtn.setOnClickListener(v -> {
            getNavController().navigate(R.id.action_search_fragment_dest_to_detail_fragment_dest);
        });

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setTitle(this.query);
    }

    private NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }

    private void setupSearchView(SearchView searchView, MenuItem searchMenuItem) {
        SearchViewCursorAdapter adapter = new SearchViewCursorAdapter(getActivity());
        searchView.setSuggestionsAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String string) {
                SearchFragment.this.searchViewModel.onQueryChanged(string);
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
                Timber.w("position: " + position);
                SearchFragment.this.query = "Adventure";
                SearchFragment.this.setTitle("Adventure");
                searchMenuItem.collapseActionView();
                return false;
            }
        });

        this.searchViewModel.getSuggestions().observe(this, adapter::setSuggestions);
    }

    private void setTitle(String query) {
        String title = query == null ? getString(R.string.search) : query;
        if (getActivity() != null) {
            getActivity().setTitle(title);
        }
    }
}
