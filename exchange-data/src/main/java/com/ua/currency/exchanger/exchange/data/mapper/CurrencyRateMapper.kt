package com.ua.currency.exchanger.exchange.data.mapper

import com.ua.currency.exchanger.exchange.data.model.CurrencyRateDataModel
import com.ua.currency.exchanger.exchange.domain.model.CurrencyRateDomainModel

class CurrencyRateMapper {

    fun dataToDomain(currencyRate: CurrencyRateDataModel): CurrencyRateDomainModel =
        CurrencyRateDomainModel(
            symbol = currencyRate.symbol,
            baseRate = currencyRate.baseRate,
        )

    fun domainToData(currencyRate: CurrencyRateDomainModel): CurrencyRateDataModel =
        CurrencyRateDataModel(
            symbol = currencyRate.symbol,
            baseRate = currencyRate.baseRate,
        )

}
