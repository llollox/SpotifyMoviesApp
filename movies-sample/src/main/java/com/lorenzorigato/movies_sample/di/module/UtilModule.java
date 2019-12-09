package com.lorenzorigato.movies_sample.di.module;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.util.IAppInfo;
import com.lorenzorigato.movies_sample.util.FakeAppInfo;

import dagger.Module;
import dagger.Provides;

@Module
public class UtilModule {

    @ApplicationScope
    @Provides
    public static IAppInfo providesAppInfo() {
        return new FakeAppInfo();
    }
}
