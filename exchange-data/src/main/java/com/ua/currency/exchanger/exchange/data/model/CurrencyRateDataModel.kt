package com.ua.currency.exchanger.exchange.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_rate")
data class CurrencyRateDataModel(
    @PrimaryKey
    val symbol: String,
    val baseRate: Double,
)
