package com.ua.currency.exchanger.exchange.domain.repository

import com.ua.currency.exchanger.exchange.domain.model.BalanceDomainModel

interface CurrencyBalanceRepository {
    fun getCurrencyBalance(currency: String): Result<BalanceDomainModel>
    fun getCurrenciesBalanceList(): List<BalanceDomainModel>
    fun saveCurrencyBalanceList(currencyBalanceList: List<BalanceDomainModel>)
    fun updateCurrencyBalance(balance: BalanceDomainModel)
}
