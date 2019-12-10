package com.lorenzorigato.movies.ui.detail;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;
import com.lorenzorigato.movies.FakeMovieRepository;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.UUID;

import static com.lorenzorigato.movies.LiveDataTestUtil.getOrAwaitValue;
import static junit.framework.TestCase.assertEquals;

public class MovieDetailViewModelTest {


    // Static **************************************************************************************
    private static final Movie MOVIE = new Movie(2,
            "Casino Royale",
            "Everyone has a past. Every legend has a beginning.",
            "Le Chiffre, a banker to the world's terrorists, is scheduled to participate in a high-stakes poker game in Montenegro, where he intends to use his winnings to establish his financial grip on the terrorist market. M sends Bond – on his maiden mission as a 00 Agent – to attend this game and prevent Le Chiffre from winning. With the help of Vesper Lynd and Felix Leiter, Bond enters the most important poker game in his already dangerous career.",
            7.3,
            false,
            "https://aforismi.meglio.it/img/film/Casino_royale.jpg");

    private static ArrayList<Actor> getActors(int movieId) {
        ArrayList<Actor> actors = new ArrayList<>();
        Actor actor1 = new Actor(UUID.randomUUID().toString(), "Daniel Craig", 2, "James Bond", "https://image.tmdb.org/t/p/w185/mr6cdu6lLRscfFUv8onVWZqaRdZ.jpg", movieId);
        Actor actor2 = new Actor(UUID.randomUUID().toString(), "Eva Green", 1, "Vesper Lynd", "https://image.tmdb.org/t/p/w185/wqK0BhMuNBvDqIg1bwT9RhYMy6L.jpg", movieId);
        Actor actor3 = new Actor(UUID.randomUUID().toString(), "Mads Mikkelsen", 2, "Le Chiffre", "https://image.tmdb.org/t/p/w185/8F1dY2rjZ1YDEKH0imDs21xdTDX.jpg", movieId);
        Actor actor4 = new Actor(UUID.randomUUID().toString(), "Judi Dench", 1, "M", "https://image.tmdb.org/t/p/w185/2is9RvJ3BQAku2EtCmyk5EZoxzT.jpg", movieId);
        Actor actor5 = new Actor(UUID.randomUUID().toString(), "Jeffrey Wright", 2, "Felix Leiter", "https://image.tmdb.org/t/p/w185/wBh9rwK3aRr1hCrSRLxxPHKzGeU.jpg", movieId);
        Actor actor6 = new Actor(UUID.randomUUID().toString(), "Caterina Murino", 1, "Solange Dimitrios", "https://image.tmdb.org/t/p/w185/4jGqHO8driLwa4rU19J0tif5ck8.jpg", movieId);
        actors.add(actor1);
        actors.add(actor2);
        actors.add(actor3);
        actors.add(actor4);
        actors.add(actor5);
        actors.add(actor6);
        return actors;
    }

    private static final MovieWithActors MOVIE_WITH_ACTORS = new MovieWithActors(MOVIE, getActors(MOVIE.getId()));


    // Class attributes ****************************************************************************
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();


    // Test methods ********************************************************************************
    @Test
    public void onToggleFavorite__whenFavoriteUpdateFails__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();

        fakeMovieRepository.setToggleFavoriteError(new Throwable());
        fakeMovieRepository.setToggleFavoriteMovie(null);
        fakeMovieRepository.setFindByIdMoviewWithActors(MOVIE_WITH_ACTORS);
        int movieId = MOVIE_WITH_ACTORS.getMovie().getId();

        MovieDetailViewModel viewModel = new MovieDetailViewModel(fakeMovieRepository, movieId);

        // Tested method
        viewModel.onToggleFavorite();

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), MovieDetailView.Status.FAVORITE_NOT_SET_ERROR);
    }

    @Test
    public void onToggleFavorite__whenAddFavoriteSucceed__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(null);
        fakeMovieRepository.setFindByIdMoviewWithActors(MOVIE_WITH_ACTORS);
        MOVIE.setFavorite(true);
        fakeMovieRepository.setToggleFavoriteMovie(MOVIE);

        MovieDetailViewModel viewModel = new MovieDetailViewModel(fakeMovieRepository, MOVIE.getId());

        // Tested method
        viewModel.onToggleFavorite();

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), MovieDetailView.Status.FAVORITE_ADD_SUCCESS);
    }

    @Test
    public void onToggleFavorite__whenRemoveFavoriteSucceed__verifStatusFavoriteNotSetError() {
        FakeMovieRepository fakeMovieRepository = new FakeMovieRepository();
        fakeMovieRepository.setToggleFavoriteError(null);
        fakeMovieRepository.setFindByIdMoviewWithActors(MOVIE_WITH_ACTORS);
        MOVIE.setFavorite(false);
        fakeMovieRepository.setToggleFavoriteMovie(MOVIE);

        MovieDetailViewModel viewModel = new MovieDetailViewModel(fakeMovieRepository, MOVIE.getId());

        // Tested method
        viewModel.onToggleFavorite();

        // Assert status is invalid genre
        assertEquals(getOrAwaitValue(viewModel.getStatus()), MovieDetailView.Status.FAVORITE_REMOVED_SUCCESS);
    }
}
