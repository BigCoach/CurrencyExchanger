package com.ua.currency.exchanger.exchange.domain.repository

import com.ua.currency.exchanger.exchange.domain.model.CurrencyRateDomainModel

interface CurrencyRateRepository {
    fun getCurrencyRateFromDb(currency: String): Result<Double>
    fun fetchCurrencyRatesFromApi(): Result<List<CurrencyRateDomainModel>>
    fun saveCurrencyRatesToDb(currencyRateDomainModelList: List<CurrencyRateDomainModel>)
}
