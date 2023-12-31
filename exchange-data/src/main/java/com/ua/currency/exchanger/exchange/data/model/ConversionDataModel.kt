package com.ua.currency.exchanger.exchange.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.ua.currency.exchanger.exchange.data.db.typeconverters.CommissionTypeConverter

@Entity(tableName = "conversion")
data class ConversionDataModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val fromCurrency: String,
    val toCurrency: String,
    val fromAmount: Double,
    val toAmount: Double,
    @TypeConverters(CommissionTypeConverter::class)
    val commission: CommissionDataModel,
    val timeStamp: Long,
)
