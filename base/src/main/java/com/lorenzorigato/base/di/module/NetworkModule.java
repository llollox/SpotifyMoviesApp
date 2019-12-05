package com.lorenzorigato.base.di.module;

import android.app.Application;

import com.lorenzorigato.base.config.interfaces.IConfiguration;
import com.lorenzorigato.base.di.scope.ApplicationScope;
import com.lorenzorigato.base.network.component.ReachabilityChecker;
import com.lorenzorigato.base.network.component.interfaces.IReachabilityChecker;
import com.lorenzorigato.base.network.service.GenreService;
import com.lorenzorigato.base.network.service.MovieService;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {

    @ApplicationScope
    @Provides
    public static MovieService providesMovieService(Retrofit retrofit) {
        return retrofit.create(MovieService.class);
    }

    @ApplicationScope
    @Provides
    public static GenreService providesGenreService(Retrofit retrofit) {
        return retrofit.create(GenreService.class);
    }

    @ApplicationScope
    @Provides
    public static Retrofit providesRetrofit(OkHttpClient okHttpClient, GsonConverterFactory gsonConverterFactory, IConfiguration configuration) {
        return new Retrofit.Builder()
                .baseUrl(configuration.getServerUrl())
                .client(okHttpClient)
                .addConverterFactory(gsonConverterFactory)
                .build();
    }

    @ApplicationScope
    @Provides
    public static OkHttpClient providesOkHttpClient(HttpLoggingInterceptor interceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
    }

    @ApplicationScope
    @Provides
    public static HttpLoggingInterceptor providesHttpLoggingInterceptor() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        return interceptor;
    }

    @ApplicationScope
    @Provides
    public static GsonConverterFactory providesGsonConverterFactory() {
        return GsonConverterFactory.create();
    }

    @ApplicationScope
    @Provides
    public static IReachabilityChecker providesReachabilityChecker(Application application) {
        return new ReachabilityChecker(application);
    }
}
