package com.ua.currency.exchanger.exchange.ui.di

import com.ua.currency.exchanger.architecture.ui.notification.mapper.NotificationToUiMapper
import com.ua.currency.exchanger.architecture.ui.view.BaseComposeHolder
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.presentation.model.ExchangePresentationNotification
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState
import com.ua.currency.exchanger.exchange.presentation.viewmodel.ExchangeViewModel
import com.ua.currency.exchanger.exchange.ui.mapper.ErrorToUiMapper

data class ExchangeDependencies(
    val exchangeViewModel: ExchangeViewModel,
    private val exchangeNotificationMapper: NotificationToUiMapper,
    val errorToUiMapper: ErrorToUiMapper,
    val coroutineContextProvider: CoroutineContextProvider,
) : BaseComposeHolder<ExchangeViewState, ExchangePresentationNotification>(
    exchangeViewModel,
    coroutineContextProvider,
    exchangeNotificationMapper
)
