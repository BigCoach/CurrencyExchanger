package com.ua.currency.exchanger.exchange.domain.repository

import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel

interface ConversionRepository {
    fun getPreviousConversionsCount(): Int
    fun saveConversion(conversion: ConversionDomainModel.Conversion)
}
