package com.lorenzorigato.movies.ui.component.movielist;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.lorenzorigato.base.model.entity.Genre;
import com.lorenzorigato.base.model.repository.interfaces.IMovieRepository;
import com.lorenzorigato.base.network.NetworkState;

public class MovieBoundaryCallback extends PagedList.BoundaryCallback<MovieViewHolder.Layout> {


    // Private class attributes ********************************************************************
    private Genre genre;
    private IMovieRepository movieRepository;
    private boolean isLastPage = false;
    private MutableLiveData<NetworkState> networkStateLiveData = new MutableLiveData<>();


    // Constructor *********************************************************************************
    public MovieBoundaryCallback(Genre genre, IMovieRepository movieRepository) {
        this.genre = genre;
        this.movieRepository = movieRepository;
    }


    // Class methods *******************************************************************************
    public MutableLiveData<NetworkState> getNetworkStateLiveData() {
        return networkStateLiveData;
    }


    // PagedList.BoundaryCallback<MovieViewHolder.Layout> methods **********************************
    @Override
    public void onZeroItemsLoaded() {
        if (!this.isLastPage) {
            this.networkStateLiveData.setValue(NetworkState.LOADING);
            this.movieRepository.updateByGenre(this.genre, -1, (error, movies, isLastPage) -> {
                this.isLastPage = isLastPage;
                if (error == null) {
                    this.networkStateLiveData.setValue(NetworkState.SUCCESS);
                }
                else {
                    this.networkStateLiveData.setValue(NetworkState.ERROR);
                }
            });
        }
    }

    @Override
    public void onItemAtEndLoaded(@NonNull MovieViewHolder.Layout itemAtEnd) {
        if (!this.isLastPage) {
            this.networkStateLiveData.setValue(NetworkState.LOADING);
            this.movieRepository.updateByGenre(this.genre, itemAtEnd.getId(), (error, movies, isLastPage) -> {
                this.isLastPage = isLastPage;
                if (error == null) {
                    this.networkStateLiveData.setValue(NetworkState.SUCCESS);
                }
                else {
                    this.networkStateLiveData.setValue(NetworkState.ERROR);
                }
            });
        }
    }
}
