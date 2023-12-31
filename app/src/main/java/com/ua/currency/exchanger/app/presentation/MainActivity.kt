package com.ua.currency.exchanger.app.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ua.currency.exchanger.app.presentation.ui.AppNavHost
import com.ua.currency.exchanger.app.presentation.ui.di.AppNavHostDependencies
import com.ua.currency.exchanger.app.presentation.ui.theme.CurrencyExchangerTheme
import com.ua.currency.exchanger.architecture.ui.notification.mapper.NotificationToUiMapper
import com.ua.currency.exchanger.coroutine.CoroutineContextProvider
import com.ua.currency.exchanger.exchange.presentation.viewmodel.ExchangeViewModel
import com.ua.currency.exchanger.exchange.ui.mapper.ErrorToUiMapper
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var exchangeViewModel: ExchangeViewModel

    @Inject
    lateinit var notificationToUiMapper: NotificationToUiMapper

    @Inject
    lateinit var errorToUiMapper: ErrorToUiMapper

    @Inject
    lateinit var coroutineContextProvider: CoroutineContextProvider


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CurrencyExchangerTheme {
                AppNavHostDependencies(
                    exchangeViewModel,
                    notificationToUiMapper,
                    errorToUiMapper,
                    coroutineContextProvider,
                ).AppNavHost()
            }
        }
    }
}
