package com.lorenzorigato.movies.ui.search;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.cursoradapter.widget.SimpleCursorAdapter;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.lorenzorigato.movies.R;
import com.lorenzorigato.movies.databinding.SearchFragmentBinding;

import timber.log.Timber;

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
        this.setupSearchView(searchView, searchItem);
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

    private void setupSearchView(SearchView searchView, MenuItem searchItem) {
        final String[] from = new String[] {"name"};
        final int[] to = new int[] {android.R.id.text1};
        CursorAdapter adapter = new SimpleCursorAdapter(getActivity(),
                android.R.layout.simple_list_item_1,
                null,
                from,
                to,
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);


        searchView.setSuggestionsAdapter(adapter);

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

        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionSelect(int position) {
                return false;
            }

            @Override
            public boolean onSuggestionClick(int position) {
                Timber.w("position: " + position);

                getActivity().setTitle("Prova");
                searchItem.collapseActionView();
                return true;
            }
        });

        searchView.setOnQueryTextListener(getOnQueryTextListener(adapter));
    }

    private SearchView.OnQueryTextListener getOnQueryTextListener(CursorAdapter adapter) {
        return new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (s.length() <= 2) {
                    return false;
                }

                if (s.length() == 3) {
                    String[] array = {"Prova", "Action", "Adventure", "Test", "War", "Science Fiction", "Fantasy", "Another Genre"};
                    setSuggestions(array, adapter);
                }
                else {
                    String[] array = {"Prova", "Action", "Adventure"};
                    setSuggestions(array, adapter);
                }

                return true;
            }
        };
    }

    private void setSuggestions(String[] suggestions, CursorAdapter adapter) {
        Cursor cursor = createCursorFromResult(suggestions);
        adapter.swapCursor(cursor);
    }

    private Cursor createCursorFromResult(String[] strings)  {
        final MatrixCursor cursor = new MatrixCursor(new String[]{ BaseColumns._ID, "name" });
        for (int i=0; i<strings.length; i++) {
            String s = strings[i];
            cursor.addRow(new Object[] {i, s});
        }

        return cursor;
    }
}
