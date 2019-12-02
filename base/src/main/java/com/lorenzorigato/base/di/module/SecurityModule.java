package com.lorenzorigato.base.di.module;

import com.lorenzorigato.base.security.AntiTampering;
import com.lorenzorigato.base.security.IAntiTampering;

import dagger.Module;
import dagger.Provides;

@Module
public class SecurityModule {

    @Provides
    public static IAntiTampering providesAntiTampering() {
        return new AntiTampering();
    }
}
