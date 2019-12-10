package com.lorenzorigato.base.model.datasource.remote;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.error.NetworkRequestError;
import com.lorenzorigato.base.network.error.NoInternetError;
import com.lorenzorigato.base.network.service.GenreService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.lorenzorigato.base.network.component.retry.ApiHelper.enqueueWithRetry;

public class GenreRemoteDataSource implements IGenreRemoteDataSource {


    // Private class attributes ********************************************************************
    private GenreService genreService;
    private IReachabilityChecker reachabilityChecker;


    // Constructor *********************************************************************************
    public GenreRemoteDataSource(GenreService genreService, IReachabilityChecker reachabilityChecker) {
        this.genreService = genreService;
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

        enqueueWithRetry(this.genreService.getAll(), new Callback<List<Genre>>() {

            @Override
            public void onResponse(@NotNull Call<List<Genre>> call, @NotNull Response<List<Genre>> response) {
                if (callback == null) {
                    return;
                }

                if (response.isSuccessful()) {
                    List<Genre> genres = response.body();
                    if (genres == null) {
                        callback.onCompleted(new NetworkRequestError(), new ArrayList<>());
                    }
                    else {
                        callback.onCompleted(null, genres);
                    }
                }
                else {
                    callback.onCompleted(new NetworkRequestError(), new ArrayList<>());
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<Genre>> call, @NotNull Throwable t) {
                if (callback != null) {
                    callback.onCompleted(t, new ArrayList<>());
                }
            }
        });
    }
}
