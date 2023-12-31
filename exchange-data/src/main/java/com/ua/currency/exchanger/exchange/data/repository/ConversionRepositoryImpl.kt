package com.ua.currency.exchanger.exchange.data.repository

import com.ua.currency.exchanger.exchange.data.db.dao.ConversionDao
import com.ua.currency.exchanger.exchange.data.mapper.ConversionToDataMapper
import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel
import com.ua.currency.exchanger.exchange.domain.repository.ConversionRepository

class ConversionRepositoryImpl(
    private val conversionDao: ConversionDao,
    private val conversionToDataMapper: ConversionToDataMapper,
) : ConversionRepository {

    override fun getPreviousConversionsCount(): Int = conversionDao.getPreviousConversionsCount()

    override fun saveConversion(conversion: ConversionDomainModel.Conversion) {
        conversionDao.saveConversion(conversionToDataMapper.toData(conversion))
    }

}
