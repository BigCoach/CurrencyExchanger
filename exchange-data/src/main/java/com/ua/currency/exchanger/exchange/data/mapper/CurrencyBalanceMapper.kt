package com.ua.currency.exchanger.exchange.data.mapper

import com.ua.currency.exchanger.exchange.data.model.CurrencyBalanceDataModel
import com.ua.currency.exchanger.exchange.domain.model.BalanceDomainModel

class CurrencyBalanceMapper {

    fun toDomain(currencyBalance: CurrencyBalanceDataModel): BalanceDomainModel =
        BalanceDomainModel(
            symbol = currencyBalance.symbol,
            balance = currencyBalance.balance,
        )

    fun toData(currencyBalance: BalanceDomainModel): CurrencyBalanceDataModel =
        CurrencyBalanceDataModel(
            symbol = currencyBalance.symbol,
            balance = currencyBalance.balance,
        )
}
