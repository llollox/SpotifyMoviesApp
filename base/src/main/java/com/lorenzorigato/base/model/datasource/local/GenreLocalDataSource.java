package com.lorenzorigato.base.model.datasource.local;

import android.annotation.SuppressLint;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.components.util.AsyncCallback;
import com.lorenzorigato.base.database.dao.GenreDao;
import com.lorenzorigato.base.model.datasource.local.interfaces.IGenreLocalDataSource;
import com.lorenzorigato.base.model.entity.Genre;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class GenreLocalDataSource implements IGenreLocalDataSource {


    // Private class attributes ********************************************************************
    private GenreDao genreDao;


    // Constructor *********************************************************************************
    public GenreLocalDataSource(GenreDao genreDao) {
        this.genreDao = genreDao;
    }


    // IGenreLocalDataSource methods ***************************************************************
    @Override
    public LiveData<Genre> findByName(String name) {
        return this.genreDao.findByNameLiveData(name);
    }

    @SuppressLint("CheckResult")
    @Override
    public void saveGenres(List<Genre> genres, AsyncCallback<List<Genre>> callback) {

        Single.create((SingleOnSubscribe<List<Genre>>) emitter -> {
            try {
                genreDao.insertAll(genres);
                emitter.onSuccess(genres);
            } catch (Throwable t) {
                emitter.onError(t);
            }
        })
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribeWith(new DisposableSingleObserver<List<Genre>>() {

            @Override
            public void onSuccess(List<Genre> genres) {
                if (callback != null) {
                    callback.onCompleted(null, genres);
                }
            }

            @Override
            public void onError(Throwable e) {
                if (callback != null) {
                    callback.onCompleted(e, null);
                }
            }
        });
    }
}
