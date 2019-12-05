package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.datastructure.Trie;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.IGenreRepository;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class SearchViewModel extends ViewModel {


    // Static **************************************************************************************
    private static final int MAX_EDIT_DISTANCE = 2;


    // Private class attributes ********************************************************************
    private String[] genres = {"Action", "Adventure", "Animation",
            "Comedy", "Crime", "Documentary", "Drama", "Family", "Fantasy", "History",
            "Horror", "Music", "Mystery", "Romance", "Science Fiction", "TV Movie",
            "Thriller", "War", "Western"};

    private IGenreRepository genreRepository;
    private Trie trie = new Trie(this.genres);
    private MutableLiveData<String> genreName = new MutableLiveData<>();
    private MutableLiveData<List<String>> suggestions = new MutableLiveData<>(new ArrayList<>());
    private SingleLiveData<SearchView.Status> status = new SingleLiveData<>();
    private MutableLiveData<SearchView.State> state = new MutableLiveData<>(new SearchView.State());


    // Constructor *********************************************************************************
    public SearchViewModel(IGenreRepository genreRepository) {
        this.genreRepository = genreRepository;
        this.setLoadingVisible(true);
        this.genreRepository.updateAll(this::handleFetchAllGenreCallback);
    }


    // Class methods *******************************************************************************
    public LiveData<String> getGenreName() { return this.genreName; }

    public LiveData<SearchView.State> getState() { return this.state; }

    public LiveData<SearchView.Status> getStatus() { return this.status; }

    public LiveData<List<String>> getSuggestions() {
        return this.suggestions;
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

    public void onQuerySubmitted(String query) {
        if (query != null && this.trie.search(query.trim())) {
            this.search(query.trim());
        }
        else {
            this.status.setValue(SearchView.Status.INVALID_GENRE_ERROR);
        }
    }

    public void onSuggestionClicked(int position) {
        List<String> suggestions = this.suggestions.getValue();
        if (suggestions != null && position >= 0 && position < suggestions.size()) {
            String genre = suggestions.get(position);
            this.search(genre);
        }
    }


    // Private class methods ***********************************************************************
    private void search(String genre) {
        this.genreName.setValue(genre);
        Timber.w("**********************************");
        Timber.w("Genre: %s", genre);
        Timber.w("**********************************");
    }

    private void handleFetchAllGenreCallback(Throwable error, List<Genre> data) {
        this.setLoadingVisible(false);
        this.setEmptyTextViewVisible(true);

        if (error == null) {
            ArrayList<String> names = new ArrayList<>();
            for (Genre genre : data) {
                names.add(genre.getName());
            }
            this.trie = new Trie(names);
        }
        else {
            this.status.setValue(SearchView.Status.GENRES_NOT_LOADED_ERROR);
        }
    }

    private void setEmptyTextViewVisible(boolean isVisible) {
        SearchView.State state = this.state.getValue();
        if (state != null) {
            state.setEmptyTextViewVisible(isVisible);
            this.state.setValue(state);
        }
    }

    private void setRecyclerViewVisible(boolean isVisible) {
        SearchView.State state = this.state.getValue();
        if (state != null) {
            state.setRecyclerViewVisible(isVisible);
            this.state.setValue(state);
        }
    }

    private void setLoadingVisible(boolean isVisible) {
        SearchView.State state = this.state.getValue();
        if (state != null) {
            state.setLoadingVisible(isVisible);
            this.state.setValue(state);
        }
    }
}
