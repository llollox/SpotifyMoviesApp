package com.lorenzorigato.base.network.component.retry;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiHelper {

    public static final int DEFAULT_RETRIES = 3;

    public static <T> void enqueueWithRetry(Call<T> call, final int retryCount, final Callback<T> callback) {
        call.enqueue(new RetryableCallback<T>(call, retryCount) {
            @Override
            public void onFinalResponse(Call<T> call, Response<T> response) {
                if (callback != null) {
                    callback.onResponse(call, response);
                }
            }

            @Override
            public void onFinalFailure(Call<T> call, Throwable t) {
                if (callback != null) {
                    callback.onFailure(call, t);
                }
            }
        });
    }

    public static <T> void enqueueWithRetry(Call<T> call, final Callback<T> callback) {
        enqueueWithRetry(call, DEFAULT_RETRIES, callback);
    }
}
