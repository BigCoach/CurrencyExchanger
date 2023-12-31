package com.ua.currency.exchanger.exchange.presentation.model

data class ConversionPresentationModel(
    val fromCurrency: String,
    val toCurrency: String,
    val fromAmount: Double,
    val toAmount: Double,
    val commission: CommissionPresentationModel,
    val timeStamp: Long,
)
