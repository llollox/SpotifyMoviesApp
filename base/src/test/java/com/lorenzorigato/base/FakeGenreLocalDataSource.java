package com.lorenzorigato.base;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.datasource.local.interfaces.IGenreLocalDataSource;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

public class FakeGenreLocalDataSource implements IGenreLocalDataSource {


    // Private class attributes ********************************************************************
    private List<Genre> saveGenresList = null;
    private Throwable saveGenresError = null;


    // Class methods *******************************************************************************
    public void setSaveGenresList(List<Genre> saveGenresList) {
        this.saveGenresList = saveGenresList;
    }

    public void setSaveGenresError(Throwable saveGenresError) {
        this.saveGenresError = saveGenresError;
    }


    // IGenreLocalDataSource methods ***************************************************************
    @Override
    public LiveData<Genre> findByName(String name) {
        return null;
    }

    @Override
    public void saveGenres(List<Genre> genres, AsyncCallback<List<Genre>> callback) {
        if (callback != null) {
            callback.onCompleted(this.saveGenresError, this.saveGenresList);
        }
    }
}
