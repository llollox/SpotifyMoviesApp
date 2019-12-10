package com.lorenzorigato.base.database.dao;

import android.content.Context;

import androidx.paging.DataSource;
import androidx.room.Room;
import androidx.room.paging.LimitOffsetDataSource;
import androidx.test.platform.app.InstrumentationRegistry;

import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.entity.GenreMovieJoin;
import com.lorenzorigato.base.model.entity.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class GenreMovieJoinTest {


    // Static **************************************************************************************
    private static Genre ACTION = new Genre(1, "Action");
    private static Genre ANIMATION = new Genre(2, "Animation");

    private static ArrayList<Genre> GENRES = new ArrayList<>();

    private static Movie BATMAN_BEGINS = new Movie(1,
            "Batman Begins",
            "Evil fears the knight.",
            "Driven by tragedy, billionaire Bruce Wayne dedicates his life to uncovering and defeating the corruption that plagues his home, Gotham City.  Unable to work within the system, he instead creates a new identity, a symbol of fear for the criminal underworld - The Batman.",
            7.5,
            false,
             "system/pictures/photos/000/000/274/medium/open-uri20191202-4543-1fu33hi?1575302477");

    private static Movie CASINO_ROYALE = new Movie(2,
            "Casino Royale",
            "Everyone has a past. Every legend has a beginning.",
            "Le Chiffre, a banker to the world's terrorists, is scheduled to participate in a high-stakes poker game in Montenegro, where he intends to use his winnings to establish his financial grip on the terrorist market. M sends Bond – on his maiden mission as a 00 Agent – to attend this game and prevent Le Chiffre from winning. With the help of Vesper Lynd and Felix Leiter, Bond enters the most important poker game in his already dangerous career.",
            7.3,
            true,
            "https://aforismi.meglio.it/img/film/Casino_royale.jpg");

    private static ArrayList<Movie> MOVIES = new ArrayList<>();
    private static ArrayList<GenreMovieJoin> GENRE_MOVIE_JOINS = new ArrayList<>();

    static {
        GENRES.add(ACTION);
        GENRES.add(ANIMATION);
        MOVIES.add(BATMAN_BEGINS);
        MOVIES.add(CASINO_ROYALE);
        GENRE_MOVIE_JOINS.add(new GenreMovieJoin(ACTION.getId(), CASINO_ROYALE.getId()));
        GENRE_MOVIE_JOINS.add(new GenreMovieJoin(ANIMATION.getId(), BATMAN_BEGINS.getId()));
    }


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
    public void findMoviesByGenreIdPaged__whenValidId__returnMoviesOfCertainGenre() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insertAll(MOVIES, GENRE_MOVIE_JOINS, new ArrayList<>());

        GenreMovieJoinDao genreMovieJoinDao = this.database.getGenreMovieJoinDao();

        DataSource.Factory<Integer, Movie> factory = genreMovieJoinDao.findMoviesByGenreIdPaged(ACTION.getId());
        List<Movie> movies = ((LimitOffsetDataSource<Movie>) factory.create()).loadRange(0, 10);

        assertEquals(1, movies.size());
        assertEquals(CASINO_ROYALE, movies.get(0));
    }

    @Test
    public void findMoviesByGenreIdPaged__whenInvalidId__returnEmptyList() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        MovieDao movieDao = this.database.getMovieDao();
        movieDao.insertAll(MOVIES, GENRE_MOVIE_JOINS, new ArrayList<>());

        GenreMovieJoinDao genreMovieJoinDao = this.database.getGenreMovieJoinDao();

        DataSource.Factory<Integer, Movie> factory = genreMovieJoinDao.findMoviesByGenreIdPaged(-1);
        List<Movie> movies = ((LimitOffsetDataSource<Movie>) factory.create()).loadRange(0, 10);

        assertEquals(0, movies.size());
    }
}
