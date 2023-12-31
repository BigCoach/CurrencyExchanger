package com.ua.currency.exchanger.exchange.data.db

import com.ua.currency.exchanger.exchange.data.db.typeconverters.CommissionTypeConverter
import com.ua.currency.exchanger.exchange.data.model.ConversionDataModel
import com.ua.currency.exchanger.exchange.data.model.CurrencyRateDataModel
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ua.currency.exchanger.exchange.data.db.dao.ConversionDao
import com.ua.currency.exchanger.exchange.data.db.dao.CurrencyBalanceDao
import com.ua.currency.exchanger.exchange.data.db.dao.CurrencyRateDao
import com.ua.currency.exchanger.exchange.data.model.CurrencyBalanceDataModel


@Database(
    entities = [
        CurrencyRateDataModel::class,
        ConversionDataModel::class,
        CurrencyBalanceDataModel::class,
    ], version = 1
)
@TypeConverters(
    CommissionTypeConverter::class,
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun conversionDao() : ConversionDao
    abstract fun currencyRateDao() : CurrencyRateDao
    abstract fun currencyBalanceDao() : CurrencyBalanceDao
}
