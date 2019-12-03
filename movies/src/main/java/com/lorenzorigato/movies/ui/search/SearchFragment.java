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
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.SearchFragmentBinding;

public class SearchFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.search_actions, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        this.setupSearchView(searchView);
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

    private NavController getNavController() {
        return NavHostFragment.findNavController(this);
    }

    private void setupSearchView(SearchView searchView) {
        // Set on text changed listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Text has changed, apply filtering?
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search
                return false;
            }
        });
    }
}
