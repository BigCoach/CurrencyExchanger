package com.ua.currency.exchanger.exchange.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ua.currency.exchanger.exchange.data.model.ConversionDataModel

@Dao
interface ConversionDao {

    @Insert
    fun saveConversion(conversionDataModel: ConversionDataModel)

    @Query("SELECT COUNT(*) FROM conversion")
    fun getPreviousConversionsCount(): Int
}
