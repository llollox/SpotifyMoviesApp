package com.lorenzorigato.base.components.architecture;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.concurrent.atomic.AtomicBoolean;

import timber.log.Timber;


/**
 * A lifecycle-aware observable that sends only new updates after subscription, used for events like
 * navigation and Snackbar messages.
 * <p>
 * This avoids a common problem with events: on configuration change (like rotation) an update
 * can be emitted if the observer is active. This LiveData only calls the observable if there's an
 * explicit call to setValue() or call().
 * <p>
 * Note that only one observer is going to be notified of changes.
 */

public class SingleLiveData<T> extends MutableLiveData<T> {

    private AtomicBoolean mPending = new AtomicBoolean(false);

    @MainThread
    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        if (hasActiveObservers()) {
            Timber.w("Multiple observers registered but only one will be notified of changes.");
        }

        // Observe the internal MutableLiveData
        super.observe(owner, value -> {
            if (SingleLiveData.this.mPending.compareAndSet(true, false)) {
                observer.onChanged(value);
            }
        });
    }

    @MainThread
    @Override
    public void setValue(T value) {
        this.mPending.set(true);
        super.setValue(value);
    }
}
