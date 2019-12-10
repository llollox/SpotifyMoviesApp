package com.lorenzorigato.movies.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import androidx.paging.DataSource;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.lorenzorigato.base.components.architecture.SingleLiveData;
import com.lorenzorigato.base.datastructure.Trie;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.ui.component.movielist.MovieBoundaryCallback;
import com.lorenzorigato.movies.ui.component.movielist.MovieLayoutMapper;
import com.lorenzorigato.movies.ui.component.movielist.MovieViewHolder;

import java.util.ArrayList;
import java.util.List;

public class SearchViewModel extends ViewModel {


    // Static **************************************************************************************
    private static final int MAX_EDIT_DISTANCE = 2;
    private static final int DATABASE_PAGE_SIZE = 20;


    // Private class attributes ********************************************************************
    private IGenreRepository genreRepository;
    private IMovieRepository movieRepository;
    private Trie trie = new Trie(new ArrayList<>());
    private MutableLiveData<String> genreName = new MutableLiveData<>();
    private LiveData<Genre> genre = Transformations.switchMap(this.genreName, name -> this.genreRepository.findByName(name));
    private LiveData<PagedList<MovieViewHolder.Layout>> layouts = Transformations.switchMap(this.genre, genre -> {
        MovieBoundaryCallback boundaryCallback = new MovieBoundaryCallback(genre, this.movieRepository);

        DataSource.Factory<Integer, MovieViewHolder.Layout> factory = this.movieRepository
                .findByGenreIdPaged(genre.getId())
                .map(movie -> this.movieLayoutMapper.mapToLayout(movie));

        PagedList.Config config = new PagedList.Config.Builder()
                .setPrefetchDistance(DATABASE_PAGE_SIZE)
                .setEnablePlaceholders(true)
                .setInitialLoadSizeHint(DATABASE_PAGE_SIZE)
                .setPageSize(DATABASE_PAGE_SIZE)
                .build();

        return new LivePagedListBuilder(factory, config)
                .setBoundaryCallback(boundaryCallback)
                .build();
    });

    private MutableLiveData<List<String>> suggestions = new MutableLiveData<>(new ArrayList<>());
    private SingleLiveData<SearchView.Status> status = new SingleLiveData<>();
    private MutableLiveData<SearchView.State> state = new MutableLiveData<>(new SearchView.State());
    private MovieLayoutMapper movieLayoutMapper = new MovieLayoutMapper();


    // Constructor *********************************************************************************
    public SearchViewModel(IGenreRepository genreRepository, IMovieRepository movieRepository) {
        this.movieRepository = movieRepository;
        this.genreRepository = genreRepository;
    }


    // Class methods *******************************************************************************
    public LiveData<String> getGenreName() { return this.genreName; }

    public LiveData<SearchView.State> getState() { return this.state; }

    public LiveData<PagedList<MovieViewHolder.Layout>> getLayouts() { return this.layouts; }

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
        else {
            this.status.setValue(SearchView.Status.INVALID_GENRE_ERROR);
        }
    }

    public void onToggleFavorite(MovieViewHolder.Layout layout) {
        this.movieRepository.toggleFavorite(layout.getId(), (error, updatedMovie) -> {
            if (error != null) {
                this.status.setValue(SearchView.Status.FAVORITE_NOT_SET_ERROR);
            }
            else {
                if (updatedMovie.isFavorite()) {
                    this.status.setValue(SearchView.Status.FAVORITE_ADD_SUCCESS);
                }
                else {
                    this.status.setValue(SearchView.Status.FAVORITE_REMOVED_SUCCESS);
                }
            }
        });
    }

    public void onViewCreated() {
        this.setLoadingVisible(true);
        this.genreRepository.updateAll(this::handleFetchAllGenreCallback);
    }


    // Private class methods ***********************************************************************
    private void search(String genre) {
        this.genreName.setValue(genre);
    }

    private void handleFetchAllGenreCallback(Throwable error, List<Genre> data) {
        this.setLoadingVisible(false);

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


    // Private state setters ***********************************************************************
    private void setLoadingVisible(boolean isVisible) {
        SearchView.State state = this.state.getValue();
        if (state != null) {
            state.setLoadingVisible(isVisible);
            this.state.setValue(state);
        }
    }
}
