package com.lorenzorigato.base.model.datasource.remote;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.error.NoInternetError;

import java.util.ArrayList;
import java.util.List;

public class GenreRemoteDataSource implements IGenreRemoteDataSource {


    // Private class attributes ********************************************************************
    private IReachabilityChecker reachabilityChecker;


    // Constructor *********************************************************************************
    public GenreRemoteDataSource(IReachabilityChecker reachabilityChecker) {
        this.reachabilityChecker = reachabilityChecker;
    }


    // IGenreRemoteDataSource methods **************************************************************
    @Override
    public void fetchAll(AsyncCallback<List<Genre>> callback) {
        if (!this.reachabilityChecker.isInternetAvailable()) {
            if (callback != null) {
                callback.onCompleted(new NoInternetError(), null);
            }

            return;
        }


        if (callback != null) {

            List<Genre> genres = new ArrayList<>();
            genres.add(new Genre(1, "Action"));
            genres.add(new Genre(2, "Adventure"));
            genres.add(new Genre(3, "Animation"));

            callback.onCompleted(null, genres);
        }
    }
}
