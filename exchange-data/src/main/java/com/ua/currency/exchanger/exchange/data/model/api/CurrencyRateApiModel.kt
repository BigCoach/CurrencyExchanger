package com.ua.currency.exchanger.exchange.data.model.api

data class CurrencyRateApiModel(
    val base: String,
    val date: String,
    val rates: Map<String, Double>
)
