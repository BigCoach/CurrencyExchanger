package com.ua.currency.exchanger.exchange.data.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.ua.currency.exchanger.exchange.data.model.CurrencyRateDataModel

@Dao
interface CurrencyRateDao {

    @Query("SELECT * FROM currency_rate WHERE symbol = :symbol")
    fun getCurrencyRate(symbol: String): CurrencyRateDataModel?

    @Transaction
    @Upsert
    fun saveCurrencyRateList(currencyRateDataModelList: List<CurrencyRateDataModel>)

}
