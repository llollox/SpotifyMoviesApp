package com.lorenzorigato.movies_sample.model.repository;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.interfaces.IGenreRepository;

import java.util.List;

public class FakeGenreRepository implements IGenreRepository {

    @Override
    public LiveData<Genre> findByName(String name) {
        return null;
    }

    @Override
    public void findByName(String name, AsyncCallback<Genre> callback) {

    }

    @Override
    public void updateAll(AsyncCallback<List<Genre>> callback) {

    }
}
