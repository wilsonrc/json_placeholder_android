package com.example.jsonapp.di

import android.content.Context
import com.example.jsonapp.common.INetworkConnectivityChecker
import com.example.jsonapp.common.NetworkConnectivityChecker
import com.example.jsonapp.data.sources.remote.service.JPHService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class JPHModule {
    @Provides
    fun provideNetworkConnectivityChecker(@ApplicationContext context: Context): INetworkConnectivityChecker {
        return NetworkConnectivityChecker(context)
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    @Provides
    fun provideOkHttpClient(logger: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }

    @Provides
    fun provideJPHService(client: OkHttpClient): JPHService {
        return Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(JPHService::class.java)
    }

}