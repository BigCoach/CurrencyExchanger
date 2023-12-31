package com.ua.currency.exchanger.app.presentation.di

import android.content.Context
import android.content.res.Resources
import com.ua.currency.exchanger.architecture.ui.notification.mapper.NotificationToUiMapper
import com.ua.currency.exchanger.exchange.ui.mapper.ErrorToUiMapper
import com.ua.currency.exchanger.exchange.ui.mapper.ExchangeNotificationToUiMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext

@Module
@InstallIn(ActivityComponent::class)
object ExchangeUiModule {

    @Provides
    fun providesExchangeNotificationToUiMapper(@ActivityContext context: Context) : NotificationToUiMapper =
        ExchangeNotificationToUiMapper(context)

    @Provides
    fun providesErrorToUiMapper(resources: Resources) = ErrorToUiMapper(resources)

}
