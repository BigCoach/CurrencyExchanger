package com.ua.currency.exchanger.exchange.domain.model

data class ConversionInputDomainModel(
    val fromCurrency: String,
    val toCurrency: String,
    val fromAmount: Double
)
