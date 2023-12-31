package com.ua.currency.exchanger.app.presentation.ui.di

import com.ua.currency.exchanger.architecture.ui.notification.mapper.NotificationToUiMapper
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.presentation.viewmodel.ExchangeViewModel
import com.ua.currency.exchanger.exchange.ui.mapper.ErrorToUiMapper

data class AppNavHostDependencies(
    val exchangeViewModel: ExchangeViewModel,
    val exchangeNotificationMapper: NotificationToUiMapper,
    val errorToUiMapper: ErrorToUiMapper,
    val coroutineContextProvider: CoroutineContextProvider,
)
