package com.lorenzorigato.movies.ui.favorites;

import androidx.annotation.NonNull;
import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.movies.FakeMovieRepository;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import static com.lorenzorigato.movies.LiveDataTestUtil.getOrAwaitValue;
import static junit.framework.TestCase.assertEquals;

public class FavoritesViewModelTest {

    private static DataSource.Factory<Integer, Movie> FAKE_DATASOURCE_FACTORY;
    static {
        ArrayList<Movie> movies = new ArrayList<>();
        movies.add(new Movie(1, "", "", "", 8.0, true, ""));

        PageKeyedDataSource<Integer, Movie> dataSource = new PageKeyedDataSource<Integer, Movie>() {
            @Override
            public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {
                callback.onResult(movies, 0, 1, -1, 1);
            }

            @Override
            public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

            }

            @Override
            public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
            }
        };

        FAKE_DATASOURCE_FACTORY = new DataSource.Factory<Integer, Movie>() {
            @NonNull
            @Override
            public DataSource<Integer, Movie> create() {
                return dataSource;
            }
        };
    }


    // Class attributes ****************************************************************************
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    // Tests methods *******************************************************************************
    @Test
    public void onToggleFavorite__whenFavoriteUpdateFails__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(new Throwable());
        fakeMovieRepository.setToggleFavoriteMovie(null);
        fakeMovieRepository.setFindFavoritesFactory(FAKE_DATASOURCE_FACTORY);

        FavoritesViewModel viewModel = new FavoritesViewModel(fakeMovieRepository);

        // Tested method
        viewModel.onToggleFavorite(0);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), FavoritesView.Status.FAVORITE_NOT_SET_ERROR);
    }

    @Test
    public void onToggleFavorite__whenAddFavoriteSucceed__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(null);
        Movie updatedMovie = new Movie(1, "", "", "", 10.0, true, "");
        fakeMovieRepository.setToggleFavoriteMovie(updatedMovie);
        fakeMovieRepository.setFindFavoritesFactory(FAKE_DATASOURCE_FACTORY);

        FavoritesViewModel viewModel = new FavoritesViewModel(fakeMovieRepository);

        // Tested method
        viewModel.onToggleFavorite(0);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), FavoritesView.Status.FAVORITE_ADD_SUCCESS);
    }

    @Test
    public void onToggleFavorite__whenRemoveFavoriteSucceed__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(null);
        Movie updatedMovie = new Movie(1, "", "", "", 10.0, false, "");
        fakeMovieRepository.setToggleFavoriteMovie(updatedMovie);
        fakeMovieRepository.setFindFavoritesFactory(FAKE_DATASOURCE_FACTORY);

        FavoritesViewModel viewModel = new FavoritesViewModel(fakeMovieRepository);

        // Tested method
        viewModel.onToggleFavorite(0);

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), FavoritesView.Status.FAVORITE_REMOVED_SUCCESS);
    }
}
