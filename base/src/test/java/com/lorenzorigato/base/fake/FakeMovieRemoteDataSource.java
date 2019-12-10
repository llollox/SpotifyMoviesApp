package com.lorenzorigato.base.fake;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IMovieRemoteDataSource;

public class FakeMovieRemoteDataSource implements IMovieRemoteDataSource {


    // Static **************************************************************************************
    public interface FetchMoviesListener {
        void beforeOnCompleted();
    }


    // Private class attributes ********************************************************************
    private FetchMoviesResponse fetchMoviesResponse;
    private Throwable fetchMoviesError;
    private FetchMoviesListener fetchMoviesListener;


    // Class methods *******************************************************************************
    public void setFetchMoviesResponse(FetchMoviesResponse fetchMoviesResponse) {
        this.fetchMoviesResponse = fetchMoviesResponse;
    }

    public void setFetchMoviesError(Throwable fetchMoviesError) {
        this.fetchMoviesError = fetchMoviesError;
    }

    public void setFetchMoviesListener(FetchMoviesListener fetchMoviesListener) {
        this.fetchMoviesListener = fetchMoviesListener;
    }


    // IMovieRemoteDataSource methods **************************************************************
    @Override
    public void fetchMovies(String genre, int afterMovieId, int pageSize, AsyncCallback<FetchMoviesResponse> callback) {
        if (this.fetchMoviesListener != null) {
            this.fetchMoviesListener.beforeOnCompleted();
        }

        if (callback != null) {
            callback.onCompleted(this.fetchMoviesError, this.fetchMoviesResponse);
        }
    }
}
