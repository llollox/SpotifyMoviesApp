package com.lorenzorigato.base.database.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Room;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.test.platform.app.InstrumentationRegistry;

import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Actor;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;
import com.lorenzorigato.base.model.entity.MovieWithActors;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.lorenzorigato.base.test.util.LiveDataTestUtil.getOrAwaitValue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

public class MovieDaoTest {


    // Static **************************************************************************************
    private static Genre ACTION = new Genre(1, "Action");

    private static ArrayList<Genre> GENRES = new ArrayList<>();

    private static Movie CASINO_ROYALE = new Movie(1,
            "Casino Royale",
            "Everyone has a past. Every legend has a beginning.",
            "Le Chiffre, a banker to the world's terrorists, is scheduled to participate in a high-stakes poker game in Montenegro, where he intends to use his winnings to establish his financial grip on the terrorist market. M sends Bond – on his maiden mission as a 00 Agent – to attend this game and prevent Le Chiffre from winning. With the help of Vesper Lynd and Felix Leiter, Bond enters the most important poker game in his already dangerous career.",
            7.3,
            true,
            "https://aforismi.meglio.it/img/film/Casino_royale.jpg");

    private static ArrayList<Movie> MOVIES = new ArrayList<>();
    private static ArrayList<GenreMovieJoin> GENRE_MOVIE_JOINS = new ArrayList<>();
    private static ArrayList<Actor> ACTORS = new ArrayList<>();

    static {
        GENRES.add(ACTION);
        MOVIES.add(CASINO_ROYALE);
        GENRE_MOVIE_JOINS.add(new GenreMovieJoin(ACTION.getId(), CASINO_ROYALE.getId()));

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


    // Class attributes ****************************************************************************
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();


    // Private class attributes ********************************************************************
    private SpotifyMoviesDatabase database;


    // Class methods *******************************************************************************
    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.database = Room.inMemoryDatabaseBuilder(appContext, SpotifyMoviesDatabase.class).build();
    }

    @After
    public void tearDown() {
        this.database.close();
    }


    // Test methods ********************************************************************************
    @Test
    public void findById__whenValidId__returnTheMovie() {
        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insert(CASINO_ROYALE);
        Movie movie = movieDao.findById(CASINO_ROYALE.getId());
        assertEquals(movie, CASINO_ROYALE);
    }

    @Test
    public void findById__whenInvalidId__returnTheMovie() {
        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insert(CASINO_ROYALE);
        Movie movie = movieDao.findById(-1);
        assertNull(movie);
    }

    @Test
    public void findByIdWithActors__whenValidId__returnTheMovieWithActors() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insertAll(MOVIES, GENRE_MOVIE_JOINS, ACTORS);

        LiveData<MovieWithActors> movieWithActorsLiveData = movieDao.findByIdWithActors(CASINO_ROYALE.getId());

        MovieWithActors movieWithActors = getOrAwaitValue(movieWithActorsLiveData);

        assertEquals(CASINO_ROYALE, movieWithActors.getMovie());
        assertEquals(ACTORS, movieWithActors.getActors());
    }

    @Test
    public void findByIdWithActors__whenInvalidId__returnNull() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insertAll(MOVIES, GENRE_MOVIE_JOINS, ACTORS);

        LiveData<MovieWithActors> movieWithActorsLiveData = movieDao.findByIdWithActors(-1);

        MovieWithActors movieWithActors = getOrAwaitValue(movieWithActorsLiveData);

        assertNull(movieWithActors);
    }

    @Test
    public void findFavorites__whenThereIsOneFavorite__returnFavoritesList() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insertAll(MOVIES, GENRE_MOVIE_JOINS, ACTORS);

        DataSource.Factory<Integer, Movie> factory = movieDao.findFavorites();
        List<Movie> movies = ((LimitOffsetDataSource<Movie>) factory.create()).loadRange(0, 10);

        assertEquals(MOVIES, movies);
    }
}
