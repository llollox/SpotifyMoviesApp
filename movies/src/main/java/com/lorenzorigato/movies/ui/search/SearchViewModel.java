package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.datastructure.Trie;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {

    private static final int MAX_EDIT_DISTANCE = 2;

    private String[] genres = {"Action", "Adventure", "Animation",
            "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History",
            "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie",
            "Thriller", "War", "Western"};

    private Trie trie = new Trie(this.genres);

    private MutableLiveData<List<String>> suggestions = new MutableLiveData<>();

    public MutableLiveData<List<String>> getSuggestions() {
        return suggestions;
    }

    public void onQueryChanged(String query) {
        if (query == null || query.trim().length() <= 2) {
            this.suggestions.setValue(new ArrayList<>());
        }
        else {
            List<String> suggestions = this.trie.startsWith(query.trim(), MAX_EDIT_DISTANCE);
            this.suggestions.setValue(suggestions);
        }
    }
}
