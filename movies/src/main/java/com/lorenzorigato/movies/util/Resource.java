package com.lorenzorigato.movies.util;

import androidx.lifecycle.LiveData;

import com.lorenzorigato.base.network.NetworkState;

public class Resource<T> {


    // Private class attributes ********************************************************************
    private LiveData<T> data;
    private LiveData<NetworkState> networkState;


    // Constructor *********************************************************************************
    public Resource(LiveData<T> data, LiveData<NetworkState> networkState) {
        this.data = data;
        this.networkState = networkState;
    }


    // Class methods *******************************************************************************
    public LiveData<T> getData() {
        return data;
    }

    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }
}
