package com.ua.currency.exchanger.exchange.data.db.typeconverters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ua.currency.exchanger.exchange.data.model.CommissionDataModel

class CommissionTypeConverter {

    private val gson = Gson()

    @TypeConverter
    fun fromCommission(item: CommissionDataModel?): String {
        if (item == null) {
            return ""
        }
        return gson.toJson(item)
    }

    @TypeConverter
    fun toCommission(item: String): CommissionDataModel? {
        if (item.isEmpty()) {
            return null
        }
        val type = object : TypeToken<CommissionDataModel>() {}.type
        return gson.fromJson(item, type)
    }
}
