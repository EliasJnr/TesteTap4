package com.eliasjr.testetap4.dagger.modules

import com.eliasjr.testetap4.api.MovieAPI
import com.eliasjr.testetap4.utilities.apiUrl
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
open class NetModule {

    @Provides
    @Singleton
    open fun provideRetrofit(): Retrofit {
        val httpClient = OkHttpClient.Builder()
        val gson = GsonBuilder()
            .setDateFormat("dd/MM/yyyy HH:mm:ss")
            .setLenient()
            .create()

        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(logging)

        return Retrofit.Builder()
            .baseUrl(apiUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(httpClient.build())
            .build()
    }


    @Provides
    open fun provideMovieService(retrofit: Retrofit): MovieAPI {
        return retrofit.create(MovieAPI::class.java)
    }

}