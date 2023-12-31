package com.ua.currency.exchanger.exchange.domain.model

import com.ua.currency.exchanger.architecture.domain.exception.DomainException

sealed interface ConversionDomainModel {
    data class Conversion(
        val fromCurrency: String,
        val toCurrency: String,
        val fromAmount: Double,
        val toAmount: Double,
        val commission: CommissionDomainModel,
        val timeStamp: Long,
    ): ConversionDomainModel
    data class Error(val exception: DomainException): ConversionDomainModel
}


