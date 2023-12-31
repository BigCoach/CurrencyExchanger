package com.ua.currency.exchanger.exchange.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.ua.currency.exchanger.exchange.data.model.CurrencyBalanceDataModel

@Dao
interface CurrencyBalanceDao {

    @Query("SELECT * FROM currency_balance")
    fun getAll(): List<CurrencyBalanceDataModel>

    @Query("SELECT * FROM currency_balance WHERE symbol = :symbol")
    fun getCurrency(symbol: String): CurrencyBalanceDataModel?

    @Transaction
    @Upsert
    fun saveCurrencyList(currencyBalanceDataModelList: List<CurrencyBalanceDataModel>)

    @Upsert
    fun updateCurrency(currencyBalanceDataModel: CurrencyBalanceDataModel)

}
