package com.lorenzorigato.movies_sample.di.module;

import com.lorenzorigato.movies_sample.ui.TestActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    @ContributesAndroidInjector
    abstract TestActivity bindTestActivity();
}
