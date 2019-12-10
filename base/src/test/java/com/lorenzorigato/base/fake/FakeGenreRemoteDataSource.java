package com.lorenzorigato.base.fake;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.remote.interfaces.IGenreRemoteDataSource;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public class FakeGenreRemoteDataSource implements IGenreRemoteDataSource {


    // Static **************************************************************************************
    public interface FetchAllListener {
        void beforeOnCompleted();
    }


    // Private class attributes ********************************************************************
    private Throwable fetchAllError = null;
    private List<Genre> fetchAllGenres = null;
    private FetchAllListener fetchAllListener = null;



    // Class methods *******************************************************************************
    public void setFetchAllError(Throwable fetchAllError) {
        this.fetchAllError = fetchAllError;
    }

    public void setFetchAllGenres(List<Genre> fetchAllGenres) {
        this.fetchAllGenres = fetchAllGenres;
    }

    public void setFetchAllListener(FetchAllListener fetchAllListener) {
        this.fetchAllListener = fetchAllListener;
    }


    // IGenreRemoteDataSource methods **************************************************************
    @Override
    public void fetchAll(AsyncCallback<List<Genre>> callback) {
        if (this.fetchAllListener != null) {
            this.fetchAllListener.beforeOnCompleted();
        }

        if (callback != null) {
            callback.onCompleted(this.fetchAllError, this.fetchAllGenres);
        }
    }
}
