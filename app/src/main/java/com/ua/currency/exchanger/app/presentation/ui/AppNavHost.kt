package com.ua.currency.exchanger.app.presentation.ui

import android.text.TextUtils.replace
import android.view.View
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ua.currency.exchanger.app.presentation.ui.di.AppNavHostDependencies
import com.ua.currency.exchanger.exchange.ui.di.ExchangeDependencies
import com.ua.currency.exchanger.exchange.ui.view.Exchange


@Composable
fun AppNavHostDependencies.AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = "exchange"
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable("exchange") {
            ExchangeDependencies(
                exchangeViewModel,
                exchangeNotificationMapper,
                errorToUiMapper,
                coroutineContextProvider,
            ).Exchange()
        }
    }
}
