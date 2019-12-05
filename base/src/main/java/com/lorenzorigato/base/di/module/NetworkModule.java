package com.lorenzorigato.base.di.module;

import android.app.Application;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.network.component.ReachabilityChecker;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {

    @ApplicationScope
    @Provides
    public static IReachabilityChecker providesReachabilityChecker(Application application) {
        return new ReachabilityChecker(application);
    }
}
