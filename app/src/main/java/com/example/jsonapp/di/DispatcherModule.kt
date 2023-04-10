package com.example.jsonapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class IODispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@InstallIn(SingletonComponent::class)
@Module
object CoroutinesScopesModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @Provides
    @IODispatcher
    fun provideIODispatchers(): CoroutineDispatcher = Dispatchers.IO

    @Singleton
    @ApplicationScope
    @Provides
    fun providesCoroutineScope(
        @DefaultDispatcher defaultDispatcher: CoroutineDispatcher
    ): CoroutineScope = CoroutineScope(SupervisorJob() + defaultDispatcher)
}
