package com.lorenzorigato.base.model.repository;

import com.lorenzorigato.base.fake.FakeMovieLocalDataSource;
import com.lorenzorigato.base.fake.FakeMovieRemoteDataSource;
import com.lorenzorigato.base.model.datasource.local.interfaces.IMovieLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.error.TaskAlreadyRunningError;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class MovieRepositoryTest {


    // Static **************************************************************************************
    private static Genre GENRE = new Genre(1, "Actor");

    private static Movie CASINO_ROYALE = new Movie(1,
            "Casino Royale",
            "Everyone has a past. Every legend has a beginning.",
            "Le Chiffre, a banker to the world's terrorists, is scheduled to participate in a high-stakes poker game in Montenegro, where he intends to use his winnings to establish his financial grip on the terrorist market. M sends Bond – on his maiden mission as a 00 Agent – to attend this game and prevent Le Chiffre from winning. With the help of Vesper Lynd and Felix Leiter, Bond enters the most important poker game in his already dangerous career.",
            7.3,
            false,
            "https://aforismi.meglio.it/img/film/Casino_royale.jpg");

    private static ArrayList<Movie> MOVIES = new ArrayList<>();
    private static ArrayList<Actor> ACTORS = new ArrayList<>();

    static {
        MOVIES.add(CASINO_ROYALE);
        Actor actor1 = new Actor(UUID.randomUUID().toString(), "Daniel Craig", 2, "James Bond", "https://image.tmdb.org/t/p/w185/mr6cdu6lLRscfFUv8onVWZqaRdZ.jpg", CASINO_ROYALE.getId());
        Actor actor2 = new Actor(UUID.randomUUID().toString(), "Eva Green", 1, "Vesper Lynd", "https://image.tmdb.org/t/p/w185/wqK0BhMuNBvDqIg1bwT9RhYMy6L.jpg", CASINO_ROYALE.getId());
        Actor actor3 = new Actor(UUID.randomUUID().toString(), "Mads Mikkelsen", 2, "Le Chiffre", "https://image.tmdb.org/t/p/w185/8F1dY2rjZ1YDEKH0imDs21xdTDX.jpg", CASINO_ROYALE.getId());
        Actor actor4 = new Actor(UUID.randomUUID().toString(), "Judi Dench", 1, "M", "https://image.tmdb.org/t/p/w185/2is9RvJ3BQAku2EtCmyk5EZoxzT.jpg", CASINO_ROYALE.getId());
        Actor actor5 = new Actor(UUID.randomUUID().toString(), "Jeffrey Wright", 2, "Felix Leiter", "https://image.tmdb.org/t/p/w185/wBh9rwK3aRr1hCrSRLxxPHKzGeU.jpg", CASINO_ROYALE.getId());
        Actor actor6 = new Actor(UUID.randomUUID().toString(), "Caterina Murino", 1, "Solange Dimitrios", "https://image.tmdb.org/t/p/w185/4jGqHO8driLwa4rU19J0tif5ck8.jpg", CASINO_ROYALE.getId());
        ACTORS.add(actor1);
        ACTORS.add(actor2);
        ACTORS.add(actor3);
        ACTORS.add(actor4);
        ACTORS.add(actor5);
        ACTORS.add(actor6);
    }


    // Private class attributes ********************************************************************
    private IMovieLocalDataSource mockMovieLocalDataSource = Mockito.mock(IMovieLocalDataSource.class);


    // Test methods ********************************************************************************
    @Test
    public void updateByGenre__whenFetchAllWhileIsAlreadyRunning__verifyTaskAlreadyRunningError() {
        FakeMovieRemoteDataSource fakeMovieRemoteDataSource = new FakeMovieRemoteDataSource();
        fakeMovieRemoteDataSource.setFetchMoviesError(null);
        fakeMovieRemoteDataSource.setFetchMoviesResponse(new IMovieRemoteDataSource.FetchMoviesResponse(MOVIES, ACTORS, true));

        MovieRepository movieRepository = new MovieRepository(this.mockMovieLocalDataSource, fakeMovieRemoteDataSource);

        fakeMovieRemoteDataSource.setFetchMoviesListener(() -> {
            movieRepository.updateByGenre(GENRE, -1, (error, movies, isLastPage) -> {
                assertTrue(error instanceof TaskAlreadyRunningError);
                assertNull(movies);
                assertFalse(isLastPage);
            });
        });

        movieRepository.updateByGenre(GENRE, -1, (error, movies, isLastPage) -> {});
    }

    @Test
    public void updateByGenre__whenRequestFails__verifyReturnError() {
        FakeMovieRemoteDataSource fakeMovieRemoteDataSource = new FakeMovieRemoteDataSource();
        fakeMovieRemoteDataSource.setFetchMoviesError(new Throwable());
        fakeMovieRemoteDataSource.setFetchMoviesResponse(null);

        MovieRepository movieRepository = new MovieRepository(this.mockMovieLocalDataSource, fakeMovieRemoteDataSource);

        movieRepository.updateByGenre(GENRE, -1, (error, movies, isLastPage) -> {
            assertNotNull(error);
            assertNull(movies);
            assertFalse(isLastPage);
        });
    }

    @Test
    public void updateByGenre__whenSaveIntoDbFails__verifyErrorReturned() {
        FakeMovieRemoteDataSource fakeMovieRemoteDataSource = new FakeMovieRemoteDataSource();
        fakeMovieRemoteDataSource.setFetchMoviesError(null);
        fakeMovieRemoteDataSource.setFetchMoviesResponse(new IMovieRemoteDataSource.FetchMoviesResponse(MOVIES, ACTORS, true));

        FakeMovieLocalDataSource fakeMovieLocalDataSource = new FakeMovieLocalDataSource();
        fakeMovieLocalDataSource.setSaveMoviesError(new Throwable());
        fakeMovieLocalDataSource.setSaveMoviesList(null);

        MovieRepository movieRepository = new MovieRepository(fakeMovieLocalDataSource, fakeMovieRemoteDataSource);

        movieRepository.updateByGenre(GENRE, -1, (error, movies, isLastPage) -> {
            assertNotNull(error);
            assertNull(movies);
            assertFalse(isLastPage);
        });
    }

    @Test
    public void updateByGenre__whenSucceed__verifyListReturned() {
        FakeMovieRemoteDataSource fakeMovieRemoteDataSource = new FakeMovieRemoteDataSource();
        fakeMovieRemoteDataSource.setFetchMoviesError(null);
        fakeMovieRemoteDataSource.setFetchMoviesResponse(new IMovieRemoteDataSource.FetchMoviesResponse(MOVIES, ACTORS, true));

        FakeMovieLocalDataSource fakeMovieLocalDataSource = new FakeMovieLocalDataSource();
        fakeMovieLocalDataSource.setSaveMoviesError(null);
        fakeMovieLocalDataSource.setSaveMoviesList(MOVIES);

        MovieRepository movieRepository = new MovieRepository(fakeMovieLocalDataSource, fakeMovieRemoteDataSource);

        movieRepository.updateByGenre(GENRE, -1, (error, movies, isLastPage) -> {
            assertNotNull(movies);
            assertNull(error);
        });
    }
}
