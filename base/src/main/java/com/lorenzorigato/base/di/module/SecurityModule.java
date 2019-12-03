package com.lorenzorigato.base.di.module;

import android.app.Application;

import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.security.ISecurityManager;
import com.lorenzorigato.base.security.SecurityManager;
import com.lorenzorigato.base.security.antitampering.AntiTampering;
import com.lorenzorigato.base.security.antitampering.IAntiTampering;
import com.lorenzorigato.base.util.IAppInfo;

import dagger.Module;
import dagger.Provides;


@Module
public class SecurityModule {

    @ApplicationScope
    @Provides
    public static IAntiTampering providesAntiTampering(
            Application application, IAppInfo appInfo) {
        return new AntiTampering(application, appInfo);
    }

    @ApplicationScope
    @Provides
    public static ISecurityManager providesSecurityManager(IAntiTampering antiTampering, IAppInfo appInfo) {
        return new SecurityManager(antiTampering, appInfo);
    }
}
