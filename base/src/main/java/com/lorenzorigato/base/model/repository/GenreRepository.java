package com.lorenzorigato.base.model.repository;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.local.IGenreLocalDataSource;
import com.lorenzorigato.base.model.datasource.remote.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.error.TaskAlreadyRunningError;

import java.util.ArrayList;
import java.util.List;

public class GenreRepository implements IGenreRepository {


    // Private class attributes ********************************************************************
    private IGenreLocalDataSource localDataSource;
    private IGenreRemoteDataSource remoteDataSource;
    private boolean isUpdateAllRunning = false;


    // Constructor *********************************************************************************
    public GenreRepository(IGenreLocalDataSource localDataSource, IGenreRemoteDataSource remoteDataSource) {
        this.localDataSource = localDataSource;
        this.remoteDataSource = remoteDataSource;
    }


    // IGenreRepository methods ********************************************************************
    @Override
    public void findByName(String name, AsyncCallback<Genre> callback) {
        this.localDataSource.findByName(name, callback);
    }

    /**
     * Genres are always fetched from network because the list can change during time.
     * This has two impacts on the application:
     *
     * - The user can search for a genre that exists on server but not into the db and the application
     *   will respond with 'Genre not valid', when it was supposed valid.
     *
     * - When new movies are fetched, if they don't find a correspondent genre into the db,
     *   the application will crash because the foreign key constraint is not matched.
     */
    @Override
    public void updateAll(AsyncCallback<List<Genre>> callback) {
        if (this.isUpdateAllRunning) {
            if (callback != null) {
                callback.onCompleted(new TaskAlreadyRunningError(), new ArrayList<>());
            }
            return;
        }

        this.isUpdateAllRunning = true;
        this.remoteDataSource.fetchAll((networkError, genres) -> {

            if (networkError == null) {

                GenreRepository.this.localDataSource.saveGenres(genres, (dbError, savedGenres) -> {
                    GenreRepository.this.isUpdateAllRunning = false;
                    if (callback != null) {
                        if (dbError == null) {
                            callback.onCompleted(null, savedGenres);
                        }
                        else {
                            callback.onCompleted(dbError, null);
                        }
                    }
                });
            }
            else {
                GenreRepository.this.isUpdateAllRunning = false;
                if (callback != null) {
                    callback.onCompleted(networkError, null);
                }
            }
        });
    }
}
