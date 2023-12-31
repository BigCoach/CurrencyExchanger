package com.ua.currency.exchanger.exchange.presentation.model

sealed interface ErrorPresentationModel {
    data object NoBalance : ErrorPresentationModel
    data object NoCurrencyRates : ErrorPresentationModel
    data object InSufficientBalance : ErrorPresentationModel
    data object Unknown : ErrorPresentationModel
}
