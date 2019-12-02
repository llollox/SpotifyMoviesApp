package com.lorenzorigato.spotifymoviesapp.di;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.security.AntiTampering;
import com.lorenzorigato.base.security.IAntiTampering;

import dagger.Module;
import dagger.Provides;


@Module
public class SecurityModule {

    @ApplicationScope
    @Provides
    public static IAntiTampering providesAntiTampering() {
        return new AntiTampering();
    }
}
