package com.ua.currency.exchanger.exchange.data.repository

import com.ua.currency.exchanger.exchange.data.db.dao.CurrencyRateDao
import com.ua.currency.exchanger.exchange.data.mapper.CurrencyRateMapper
import com.ua.currency.exchanger.exchange.data.model.api.CurrencyRateApiModel
import com.ua.currency.exchanger.exchange.data.network.CurrencyRateService
import com.ua.currency.exchanger.exchange.domain.model.CurrencyRateDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.CurrencyRateRepository
import java.lang.Exception

class CurrencyRateRepositoryImpl(
    private val currencyRateDao: CurrencyRateDao,
    private val currencyRateMapper: CurrencyRateMapper,
    private val currencyRateService: CurrencyRateService,
) : CurrencyRateRepository {

    override fun getCurrencyRateFromDb(currency: String): Result<Double> {
        val currencyRate = currencyRateDao.getCurrencyRate(currency)
        return if (currencyRate != null) {
            Result.success(currencyRate.baseRate)
        } else {
            Result.failure(Throwable("Currency rate not found"))
        }
    }

    override fun fetchCurrencyRatesFromApi(): Result<List<CurrencyRateDomainModel>> {
        val currencyRates: CurrencyRateApiModel = try {
            currencyRateService.getCurrencyRates().execute().body()
        } catch (e: Exception) {
            null
        } ?: return Result.failure(Throwable("Currency rates not found"))
        val currencyRateDomainModelList = mutableListOf<CurrencyRateDomainModel>()
        currencyRates.rates.entries.forEach {
            currencyRateDomainModelList.add(CurrencyRateDomainModel(it.key, it.value))
        }
        return Result.success(currencyRateDomainModelList)
    }

    override fun saveCurrencyRatesToDb(currencyRateDomainModelList: List<CurrencyRateDomainModel>) {
        currencyRateDao.saveCurrencyRateList(currencyRateDomainModelList.map {
            currencyRateMapper.domainToData(
                it
            )
        })
    }
}


