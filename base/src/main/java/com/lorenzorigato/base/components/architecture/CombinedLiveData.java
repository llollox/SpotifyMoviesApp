package com.lorenzorigato.base.components.architecture;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

public class CombinedLiveData<T, K, S> extends MediatorLiveData<S> {

    public interface Transformation<T, K, S> {
        S combine(T data1, K data2);
    }

    private T data1 = null;
    private K data2 = null;

    public CombinedLiveData(LiveData<T> source1, LiveData<K> source2, Transformation<T, K, S> transformation) {
        super.addSource(source1, t -> {
            data1 = t;
            setValue(transformation.combine(data1, data2));
        });

        super.addSource(source2, t -> {
            data2 = t;
            setValue(transformation.combine(data1, data2));
        });
    }
}
