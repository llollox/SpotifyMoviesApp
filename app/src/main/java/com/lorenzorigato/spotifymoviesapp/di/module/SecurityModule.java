package com.lorenzorigato.spotifymoviesapp.di.module;

import android.app.Application;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.spotifymoviesapp.security.AntiTampering;
import com.lorenzorigato.base.security.IAntiTampering;

import dagger.Module;
import dagger.Provides;


@Module
public class SecurityModule {

    @ApplicationScope
    @Provides
    public static IAntiTampering providesAntiTampering(Application application) {
        return new AntiTampering(application);
    }
}
