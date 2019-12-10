package com.lorenzorigato.base.network.component.retry;

import org.jetbrains.annotations.NotNull;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public abstract class RetryableCallback<T> implements Callback<T> {


    // Private class attributes ********************************************************************
    private int totalRetries;
    private final Call<T> call;
    private int retryCount = 0;


    // Constructor *********************************************************************************
    public RetryableCallback(Call<T> call, int totalRetries) {
        this.call = call;
        this.totalRetries = totalRetries;
    }


    // Class methods *******************************************************************************
    public abstract void onFinalResponse(Call<T> call, Response<T> response);
    public abstract void onFinalFailure(Call<T> call, Throwable t);


    // Callback methods ****************************************************************************
    @Override
    public void onResponse(@NotNull Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onFinalResponse(call, response);
        }
        else {
            if (this.retryCount++ < this.totalRetries) {
                this.retry();
            }
            else {
                this.onFinalResponse(call, response);
            }
        }
    }

    @Override
    public void onFailure(@NotNull Call<T> call, @NotNull Throwable throwable) {
        if (this.retryCount++ < this.totalRetries) {
            this.retry();
        }
        else {
            this.onFinalFailure(call, throwable);
        }
    }


    // Private class methods ***********************************************************************
    private void retry() {
        this.call.clone().enqueue(this);
    }
}
