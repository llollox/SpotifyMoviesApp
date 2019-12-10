package com.lorenzorigato.base.database.dao;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.platform.app.InstrumentationRegistry;

import com.lorenzorigato.base.database.SpotifyMoviesDatabase;
import com.lorenzorigato.base.model.entity.Genre;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.lorenzorigato.base.test.util.LiveDataTestUtil.getOrAwaitValue;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNull;

@RunWith(AndroidJUnit4ClassRunner.class)
public class GenreDaoTest {


    // Static **************************************************************************************
    private static List<Genre> GENRES = new ArrayList<>();

    static {
        GENRES.add(new Genre(1, "Action"));
        GENRES.add(new Genre(2, "Adventure"));
        GENRES.add(new Genre(3, "Animation"));
    }


    // Class attributes ****************************************************************************
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private SpotifyMoviesDatabase database;


    // Test methods ********************************************************************************
    @Before
    public void setup() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        this.database = Room.inMemoryDatabaseBuilder(appContext, SpotifyMoviesDatabase.class).build();
    }

    @After
    public void tearDown() {
        this.database.close();
    }

    @Test
    public void findByName__withValidName__verifyItReturnsTheGenre() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        Genre genre = getOrAwaitValue(genreDao.findByNameLiveData("Action"));
        assertEquals(genre, GENRES.get(0));
    }

    @Test
    public void findByName__withInvalidName__verifyItReturnsNull() {
        GenreDao genreDao = this.database.getGenreDao();
        genreDao.insertAll(GENRES);

        Genre genre = getOrAwaitValue(genreDao.findByNameLiveData("Unknown"));
        assertNull(genre);
    }
}
