package com.ua.currency.exchanger.app.presentation.di

import com.ua.currency.exchanger.architecture.domain.UseCaseExecutor
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object ArchitectureModule {
    @Provides
    fun providesCoroutineContextProvider(): CoroutineContextProvider =
        CoroutineContextProvider.Default

    @Provides
    fun providesUseCaseExecutor() = UseCaseExecutor()
}
