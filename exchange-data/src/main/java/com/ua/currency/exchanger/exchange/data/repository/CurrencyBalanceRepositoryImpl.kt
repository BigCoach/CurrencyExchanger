package com.ua.currency.exchanger.exchange.data.repository

import com.ua.currency.exchanger.exchange.data.db.dao.CurrencyBalanceDao
import com.ua.currency.exchanger.exchange.data.mapper.CurrencyBalanceMapper
import com.ua.currency.exchanger.exchange.domain.model.BalanceDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyBalanceRepository

class CurrencyBalanceRepositoryImpl(
    private val currencyBalanceDao: CurrencyBalanceDao,
    private val currencyBalanceMapper: CurrencyBalanceMapper,
) : CurrencyBalanceRepository {

    override fun getCurrencyBalance(currency: String): Result<BalanceDomainModel> {
        val currencyBalance = currencyBalanceDao.getCurrency(currency)
        return if(currencyBalance != null) {
            Result.success(currencyBalanceMapper.toDomain(currencyBalance))
        } else {
            Result.failure(Throwable("Currency balance not found"))
        }
    }

    override fun getCurrenciesBalanceList(): List<BalanceDomainModel> {
        return currencyBalanceDao.getAll().map { currencyBalanceMapper.toDomain(it) }
    }

    override fun saveCurrencyBalanceList(currencyBalanceList: List<BalanceDomainModel>) {
        currencyBalanceDao.saveCurrencyList(currencyBalanceList.map { currencyBalanceMapper.toData(it) })
    }

    override fun updateCurrencyBalance(balance: BalanceDomainModel) {
        currencyBalanceDao.updateCurrency(currencyBalanceMapper.toData(balance))
    }
}
