package com.lorenzorigato.movies.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.FakeGenreRepository;
import com.lorenzorigato.movies.FakeMovieRepository;
import com.lorenzorigato.movies.ui.search.SearchView;
import com.lorenzorigato.movies.ui.search.SearchViewModel;

import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.lorenzorigato.movies.LiveDataTestUtil.getOrAwaitValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SearchViewModelTest {


    // Static **************************************************************************************
    private static List<Genre> VALID_GENRES = new ArrayList<>();

    static {
        VALID_GENRES.add(new Genre(1, "Action"));
        VALID_GENRES.add(new Genre(2, "Adventure"));
        VALID_GENRES.add(new Genre(3, "Animation"));
    }




    // Class attributes ****************************************************************************
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    // Private class attributes ********************************************************************
    private IGenreRepository mockGenreRepository = Mockito.mock(IGenreRepository.class);
    private IMovieRepository mockMovieRepository = Mockito.mock(IMovieRepository.class);



    // Tests methods *******************************************************************************
    @Test
    public void onViewCreated__whenGenreLoadFails__verifStatusIsGenreLoadError() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(new Throwable());
        fakeGenreRepository.setUpdateAllGenres(null);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        assertNull(getOrAwaitValue(viewModel.getStatus()));

        viewModel.onViewCreated();

        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.GENRES_NOT_LOADED_ERROR);
    }


    @Test
    public void onViewCreated__whenGenreLoadSucceed__verifStatusIsStillNull() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        assertNull(getOrAwaitValue(viewModel.getStatus()));

        viewModel.onViewCreated();

        assertNull(getOrAwaitValue(viewModel.getStatus()));
    }

    @Test
    public void onQueryChanged__withNullGenre__verifSuggestionsIsEmpty() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        viewModel.onQueryChanged(null);

        assertEquals(getOrAwaitValue(viewModel.getSuggestions()), new ArrayList<>());
    }

    @Test
    public void onQueryChanged__withEmptyGenre__verifSuggestionsIsEmpty() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        viewModel.onQueryChanged("");

        assertEquals(getOrAwaitValue(viewModel.getSuggestions()), new ArrayList<>());
    }

    @Test
    public void onQueryChanged__withTooShortGenre__verifSuggestionsIsEmpty() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        viewModel.onQueryChanged("a");

        assertEquals(getOrAwaitValue(viewModel.getSuggestions()), new ArrayList<>());
    }

    @Test
    public void onQueryChanged__withInvalidGenre__verifSuggestionsIsEmpty() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        viewModel.onQueryChanged("Drama");

        assertEquals(getOrAwaitValue(viewModel.getSuggestions()), new ArrayList<>());
    }

    @Test
    public void onQueryChanged__withValidGenre__verifSuggestionsSetProperly() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onQueryChanged("Action");

        // Assert status is not changed
        assertNull(getOrAwaitValue(viewModel.getStatus()));

        ArrayList<String> expectedSuggestions = new ArrayList<>();
        expectedSuggestions.add("Action");

        // Assert suggestions are returned properly
        assertEquals(expectedSuggestions, getOrAwaitValue(viewModel.getSuggestions()));
    }

    @Test
    public void onQuerySubmitted__withNullGenre__verifStatusIsInvalidGenre() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onQuerySubmitted(null);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.INVALID_GENRE_ERROR);
    }

    @Test
    public void onQuerySubmitted__withEmptyGenre__verifStatusIsInvalidGenre() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onQuerySubmitted("");

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.INVALID_GENRE_ERROR);
    }

    @Test
    public void onQuerySubmitted__withInvalidGenre__verifStatusIsInvalidGenre() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onQuerySubmitted("A");

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.INVALID_GENRE_ERROR);
    }

    @Test
    public void onQuerySubmitted__withValidGenre__verifStatusIsNull() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onQuerySubmitted("Action");

        // Assert status is invalid genre
        assertNull(getOrAwaitValue(viewModel.getStatus()));
    }

    @Test
    public void onSuggestionClicked__withNegativePosition__verifStatusIsInvalidGenreError() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onSuggestionClicked(-1);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.INVALID_GENRE_ERROR);
    }

    @Test
    public void onSuggestionClicked__withPositionOutOfBounds__verifStatusIsInvalidGenreError() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        // Tested method
        viewModel.onSuggestionClicked(100);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.INVALID_GENRE_ERROR);
    }

    @Test
    public void onSuggestionClicked__withValidPosition__verifStatusIsStillNull() {
        FakeGenreRepository fakeGenreRepository = new FakeGenreRepository();
        fakeGenreRepository.setUpdateAllError(null);
        fakeGenreRepository.setUpdateAllGenres(VALID_GENRES);

        SearchViewModel viewModel = new SearchViewModel(fakeGenreRepository, this.mockMovieRepository);

        // To load genres
        viewModel.onViewCreated();

        viewModel.onQueryChanged("Action");

        // Tested method
        viewModel.onSuggestionClicked(0);

        // Assert status is invalid genre
        assertNull(getOrAwaitValue(viewModel.getStatus()));
    }

    @Test
    public void onToggleFavorite__whenFavoriteUpdateFails__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(new Throwable());
        fakeMovieRepository.setToggleFavoriteMovie(null);

        SearchViewModel viewModel = new SearchViewModel(this.mockGenreRepository, fakeMovieRepository);

        // Tested method
        viewModel.onToggleFavorite(0);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.FAVORITE_NOT_SET_ERROR);
    }

    @Test
    public void onToggleFavorite__whenAddFavoriteSucceed__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(null);
        Movie updatedMovie = new Movie(1, "", "", "", 10.0, true, "");
        fakeMovieRepository.setToggleFavoriteMovie(updatedMovie);

        SearchViewModel viewModel = new SearchViewModel(this.mockGenreRepository, fakeMovieRepository);

        // Tested method
        viewModel.onToggleFavorite(0);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.FAVORITE_ADD_SUCCESS);
    }

    @Test
    public void onToggleFavorite__whenRemoveFavoriteSucceed__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(null);
        Movie updatedMovie = new Movie(1, "", "", "", 10.0, false, "");
        fakeMovieRepository.setToggleFavoriteMovie(updatedMovie);

        SearchViewModel viewModel = new SearchViewModel(this.mockGenreRepository, fakeMovieRepository);

        // Tested method
        viewModel.onToggleFavorite(0);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), SearchView.Status.FAVORITE_REMOVED_SUCCESS);
    }
}
