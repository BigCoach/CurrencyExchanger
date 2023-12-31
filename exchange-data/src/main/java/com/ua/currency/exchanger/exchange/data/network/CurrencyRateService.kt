package com.ua.currency.exchanger.exchange.data.network

import com.ua.currency.exchanger.exchange.data.model.api.CurrencyRateApiModel
import retrofit2.Call
import retrofit2.http.GET

interface CurrencyRateService {

    @GET("/tasks/api/currency-exchange-rates")
    fun getCurrencyRates(): Call<CurrencyRateApiModel>
}
