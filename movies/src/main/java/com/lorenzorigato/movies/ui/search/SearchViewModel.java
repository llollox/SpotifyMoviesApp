package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.datastructure.Trie;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.component.movielist.MovieLayoutMapper;
import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {


    // Static **************************************************************************************
    private static final int MAX_EDIT_DISTANCE = 2;


    // Private class attributes ********************************************************************
    private IGenreRepository genreRepository;
    private IMovieRepository movieRepository;
    private Trie trie = new Trie(new ArrayList<>());
    private MutableLiveData<String> genreName = new MutableLiveData<>();

    private LiveData<Genre> genre = Transformations.switchMap(this.genreName, name -> genreRepository.findByName(name));
    private LiveData<List<Movie>> movies = Transformations.switchMap(this.genre, genre -> movieRepository.findByGenreId(genre.getId()));

    private MutableLiveData<List<String>> suggestions = new MutableLiveData<>(new ArrayList<>());
    private SingleLiveData<SearchView.Status> status = new SingleLiveData<>();
    private MutableLiveData<SearchView.State> state = new MutableLiveData<>(new SearchView.State());
    private boolean _hasLoadedAllMovies = false;

    private MovieLayoutMapper movieLayoutMapper = new MovieLayoutMapper();
    private LiveData<List<MovieViewHolder.Layout>> layouts = Transformations.map(this.movies, movies -> {
        ArrayList<MovieViewHolder.Layout> layouts = new ArrayList<>();
        for (Movie movie : movies) {
            boolean isTop = movie.getRating() > 7.0;
            MovieViewHolder.Layout layout = movieLayoutMapper.mapToLayout(movie, isTop);
            layouts.add(layout);
        }
        return layouts;
    });


    // Constructor *********************************************************************************
    public SearchViewModel(IGenreRepository genreRepository, IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
        this.setLoadingVisible(true);
        this.genreRepository.updateAll(this::handleFetchAllGenreCallback);
    }


    // Class methods *******************************************************************************
    public boolean hasLoadedAllMovies() { return this._hasLoadedAllMovies; }

    public boolean isUpdateMoviesRunning() { return this.movieRepository.isUpdateByGenreRunning(); }

    public LiveData<String> getGenreName() { return this.genreName; }

    public LiveData<SearchView.State> getState() { return this.state; }

    public LiveData<List<MovieViewHolder.Layout>> getLayouts() { return this.layouts; }

    public LiveData<SearchView.Status> getStatus() { return this.status; }

    public LiveData<List<String>> getSuggestions() {
        return this.suggestions;
    }

    public void onLoadMore() {
        List<Movie> currentMovies = this.movies.getValue();
        int offset = currentMovies != null ? currentMovies.size() : 0;

        this.movieRepository.updateByGenre(this.genreName.getValue(), offset, (error, movies, hasLoadAllMovies) -> {
            if (error == null) {
                this._hasLoadedAllMovies = hasLoadAllMovies;
                this.appendMovies(movies);
            }
            else {
                this.status.setValue(SearchView.Status.MOVIES_NOT_LOADED_ERROR);
            }
        });
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

    public void onToggleFavorite(MovieViewHolder.Layout layout) {
        Movie movie = this.findMovieById(this.movies.getValue(), layout.getId());
        if (movie != null) {
            movie.setFavorite(!movie.isFavorite());
            this.movieRepository.update(movie, (error, data) -> {
                if (error != null) {
                    this.status.setValue(SearchView.Status.FAVORITE_NOT_SET_ERROR);
                }
            });
        }
    }


    // Private class methods ***********************************************************************
    private void appendMovies(List<Movie> newMovies) {
        ArrayList<Movie> movies = new ArrayList<>();
        List<Movie> currentMovies = this.movies.getValue();
        if (currentMovies != null) {
            movies.addAll(currentMovies);
        }
        movies.addAll(newMovies);
        this.setMovies(movies);
    }

    private Movie findMovieById(List<Movie> movies, int movieId) {
        if (movies == null || movies.isEmpty()) {
            return null;
        }

        for (Movie movie : movies) {
            if (movie.getId() == movieId) {
                return movie;
            }
        }

        return null;
    }

    private void search(String genre) {
        this.genreName.setValue(genre);

        this.setEmptyTextViewVisible(false);
        this.setLoadingVisible(true);

        this.movieRepository.updateByGenre(genre, 0, (error, movies, hasLoadAllMovies) -> {

            this.setLoadingVisible(false);

            if (error == null) {
                this._hasLoadedAllMovies = hasLoadAllMovies;
                this.setMovies(movies);
            }
            else {
                this.setEmptyTextViewVisible(true);
                this.status.postValue(SearchView.Status.MOVIES_NOT_LOADED_ERROR);
            }

        });
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

    private void setMovies(List<Movie> movies) {
        this.setEmptyTextViewVisible(movies.isEmpty());
        this.setRecyclerViewVisible(!movies.isEmpty());
//        this.movies.setValue(movies);
    }

    // Private state setters ***********************************************************************
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
