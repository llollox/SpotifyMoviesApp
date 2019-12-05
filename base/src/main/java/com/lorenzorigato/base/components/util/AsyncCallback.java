package com.lorenzorigato.base.components.util;

public interface AsyncCallback<T> {
    void onCompleted(Throwable error, T data);
}
