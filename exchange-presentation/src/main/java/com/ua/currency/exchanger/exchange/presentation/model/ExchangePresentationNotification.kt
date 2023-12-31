package com.ua.currency.exchanger.exchange.presentation.model

import com.ua.currency.exchanger.architecture.presentation.notification.PresentationNotification

sealed interface ExchangePresentationNotification : PresentationNotification {
    data class CurrencyConverted(
        val convertedFromAmount: Double,
        val convertedToAmount: Double,
        val convertedFromCurrency: String,
        val convertedToCurrency: String,
        val commissionAmount: Double,
        val commissionCurrency: String,
        val timeStamp: Long
    ) : ExchangePresentationNotification
    data class CurrencyBalanceNotFound(val message: String) : ExchangePresentationNotification
    data object FailedToExecuteConversion : ExchangePresentationNotification
    data object ConversionNotCalculated : ExchangePresentationNotification
    data object FailedToUpdateRates : ExchangePresentationNotification
    data object AmountToSellExceedsBalance : ExchangePresentationNotification
}
