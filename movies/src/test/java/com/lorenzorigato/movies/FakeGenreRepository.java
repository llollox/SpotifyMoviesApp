package com.lorenzorigato.movies;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;

import java.util.List;

public class FakeGenreRepository implements IGenreRepository {


    // Private class attributes ********************************************************************
    private Genre findByName = null;
    private Throwable updateAllError = null;
    private List<Genre> updateAllGenres = null;


    // Class methods *******************************************************************************
    public void setFindByName(Genre findByName) {
        this.findByName = findByName;
    }

    public void setUpdateAllError(Throwable updateAllError) {
        this.updateAllError = updateAllError;
    }

    public void setUpdateAllGenres(List<Genre> updateAllGenres) {
        this.updateAllGenres = updateAllGenres;
    }


    // IGenreRepository methods ********************************************************************
    @Override
    public LiveData<Genre> findByName(String name) {
        return new MutableLiveData<>(this.findByName);
    }

    @Override
    public void updateAll(AsyncCallback<List<Genre>> callback) {
        if (callback != null) {
            callback.onCompleted(this.updateAllError, this.updateAllGenres);
        }
    }
}
