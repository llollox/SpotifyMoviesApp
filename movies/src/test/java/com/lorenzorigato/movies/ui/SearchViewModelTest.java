package com.lorenzorigato.movies.ui;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.movies.FakeGenreRepository;
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
    public void onQueryChanged__withNullGenre__verifStatusIsInvalidGenre() {
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
    public void onQueryChanged__withEmptyGenre__verifStatusIsInvalidGenre() {
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
    public void onQueryChanged__withTooShortGenre__verifStatusIsInvalidGenre() {
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
    public void onQueryChanged__withInvalidGenre__verifStatusIsInvalidGenre() {
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
}
