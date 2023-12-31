package com.ua.currency.exchanger.exchange.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_balance")
data class CurrencyBalanceDataModel (
    @PrimaryKey
    val symbol: String,
    val balance: Double,
)
