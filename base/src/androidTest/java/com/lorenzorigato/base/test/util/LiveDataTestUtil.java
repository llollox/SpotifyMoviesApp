package com.lorenzorigato.base.test.util;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class LiveDataTestUtil {


    private static class DataContainer<T> {
        T data;

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }
    }


    public static <T> T getOrAwaitValue(LiveData<T> liveData) {
        return getOrAwaitValue(liveData, 2, TimeUnit.SECONDS);
    }

    public static <T> T getOrAwaitValue(LiveData<T> liveData, long time, TimeUnit timeUnit) {

        DataContainer<T> dataContainer = new DataContainer<>();
        CountDownLatch latch = new CountDownLatch(1);

        Observer<T> observer = new Observer<T>() {

            @Override
            public void onChanged(T t) {
                dataContainer.setData(t);
                latch.countDown();
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);

        try {
            if (!latch.await(time, timeUnit)) {
                return null;
            }
        }
        catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
        finally {
            liveData.removeObserver(observer);
        }

        return dataContainer.getData();
    }

}
