package com.ua.currency.exchanger.exchange.presentation.model

sealed interface ExchangeViewState {
        data object Loading : ExchangeViewState
        data class Loaded(
            val isRefreshing: Boolean,
            val balances: List<BalancePresentationModel>,
            val firstSelectedCurrency: String,
            val secondSelectedCurrency: String,
            val amountToSellMaxValue: Double,
            val amountToSell: Double,
            val amountToReceive: Double,
            val calculatedConversion: ConversionPresentationModel?,
        ) : ExchangeViewState
        data class Error(val error: ErrorPresentationModel) : ExchangeViewState
}
