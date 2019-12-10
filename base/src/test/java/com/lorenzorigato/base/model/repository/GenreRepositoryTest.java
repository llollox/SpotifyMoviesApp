package com.lorenzorigato.base.model.repository;

import com.lorenzorigato.base.fake.FakeGenreLocalDataSource;
import com.lorenzorigato.base.fake.FakeGenreRemoteDataSource;
import com.lorenzorigato.base.model.datasource.local.interfaces.IGenreLocalDataSource;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.error.TaskAlreadyRunningError;
import com.lorenzorigato.base.network.error.NetworkRequestError;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static junit.framework.TestCase.assertTrue;

public class GenreRepositoryTest {


    // Static **************************************************************************************
    private static List<Genre> VALID_GENRES = new ArrayList<>();

    static {
        VALID_GENRES.add(new Genre(1, "Action"));
        VALID_GENRES.add(new Genre(2, "Adventure"));
        VALID_GENRES.add(new Genre(3, "Animation"));
    }


    // Private class attributes ********************************************************************
    private IGenreLocalDataSource mockGenreLocalDataSource = Mockito.mock(IGenreLocalDataSource.class);


    // Test methods ********************************************************************************
    @Test
    public void updateAll__whenFetchAllWhileIsAlreadyRunning__verifyTaskAlreadyRunningError() {
        FakeGenreRemoteDataSource fakeGenreRemoteDataSource = new FakeGenreRemoteDataSource();
        fakeGenreRemoteDataSource.setFetchAllError(null);
        fakeGenreRemoteDataSource.setFetchAllGenres(VALID_GENRES);

        GenreRepository genreRepository = new GenreRepository(this.mockGenreLocalDataSource, fakeGenreRemoteDataSource);

        fakeGenreRemoteDataSource.setFetchAllListener(() -> {
            genreRepository.updateAll((error, data) -> {
                assertTrue(error instanceof TaskAlreadyRunningError);
            });
        });

        genreRepository.updateAll((error, data) -> { });
    }

    @Test
    public void updateAll__whenRequestFails__verifyReturnError() {
        FakeGenreRemoteDataSource fakeGenreRemoteDataSource = new FakeGenreRemoteDataSource();
        fakeGenreRemoteDataSource.setFetchAllError(new NetworkRequestError());
        fakeGenreRemoteDataSource.setFetchAllGenres(null);

        GenreRepository genreRepository = new GenreRepository(this.mockGenreLocalDataSource, fakeGenreRemoteDataSource);

        genreRepository.updateAll((error, data) -> {
            assertNotNull(error);
            assertNull(data);
        });
    }

    @Test
    public void updateAll__whenSaveIntoDbFails__verifyErrorReturned() {
        FakeGenreRemoteDataSource fakeGenreRemoteDataSource = new FakeGenreRemoteDataSource();
        fakeGenreRemoteDataSource.setFetchAllError(null);
        fakeGenreRemoteDataSource.setFetchAllGenres(VALID_GENRES);

        FakeGenreLocalDataSource fakeGenreLocalDataSource = new FakeGenreLocalDataSource();
        fakeGenreLocalDataSource.setSaveGenresError(new Throwable());
        fakeGenreLocalDataSource.setSaveGenresList(null);

        GenreRepository genreRepository = new GenreRepository(fakeGenreLocalDataSource, fakeGenreRemoteDataSource);

        genreRepository.updateAll((error, data) -> {
            assertNotNull(error);
            assertNull(data);
        });
    }

    @Test
    public void updateAll__whenSucceed__verifyListReturned() {
        FakeGenreRemoteDataSource fakeGenreRemoteDataSource = new FakeGenreRemoteDataSource();
        fakeGenreRemoteDataSource.setFetchAllError(null);
        fakeGenreRemoteDataSource.setFetchAllGenres(VALID_GENRES);

        FakeGenreLocalDataSource fakeGenreLocalDataSource = new FakeGenreLocalDataSource();
        fakeGenreLocalDataSource.setSaveGenresError(null);
        fakeGenreLocalDataSource.setSaveGenresList(VALID_GENRES);

        GenreRepository genreRepository = new GenreRepository(this.mockGenreLocalDataSource, fakeGenreRemoteDataSource);

        genreRepository.updateAll((error, data) -> {
            assertNull(error);
            assertNotNull(data);
            assertEquals(VALID_GENRES, data);
        });
    }
}
