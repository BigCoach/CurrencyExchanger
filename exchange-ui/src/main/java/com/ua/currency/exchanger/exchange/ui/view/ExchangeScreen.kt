package com.ua.currency.exchanger.exchange.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.ua.currency.exchanger.architecture.ui.view.ScreenEnterObserver
import com.ua.currency.exchanger.exchange.presentation.model.ExchangeViewState
import com.ua.currency.exchanger.exchange.ui.content.ErrorContentContainer
import com.ua.currency.exchanger.exchange.ui.content.LoadedContentContainer
import com.ua.currency.exchanger.exchange.ui.di.ExchangeDependencies
import com.ua.currency.exchanger.exchange.ui.widget.LoadingAnimationContainer

@Composable
fun ExchangeDependencies.Exchange() {

    ScreenEnterObserver {
        exchangeViewModel.onEnter()
    }

    ViewModelObserver()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
            .imePadding()
    ) {

        val viewState = exchangeViewModel.viewState.collectAsState(
            ExchangeViewState.Loading,
            coroutineContextProvider.main
        )

        when(val viewStateValue = viewState.value) {
            is ExchangeViewState.Loaded -> {
                LoadedContentContainer(
                    state = viewStateValue,
                    onFirstCurrencySelected = { currency ->
                        exchangeViewModel.onFirstCurrencyChangedAction(currency, viewStateValue)
                    },
                    onSecondCurrencySelected = { currency ->
                        exchangeViewModel.onSecondCurrencyChangedAction(currency, viewStateValue)
                    },
                    onAmountChanged = { amount ->
                        exchangeViewModel.onSellAmountChangedAction(amount, viewStateValue)
                    },
                    onExchangeClicked = {
                        exchangeViewModel.onSubmitAction(viewStateValue)
                    }
                )
            }
            is ExchangeViewState.Error -> {
                ErrorContentContainer(
                    visible = true,
                    errorText = viewStateValue.let(errorToUiMapper::toUi)
                )
            }
            is ExchangeViewState.Loading -> {
                LoadingAnimationContainer()
            }
        }
    }

}
