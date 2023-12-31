package com.ua.currency.exchanger.exchange.data.mapper

import com.ua.currency.exchanger.exchange.data.model.ConversionDataModel
import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel

class ConversionToDataMapper(
    private val commissionToDataMapper: CommissionToDataMapper,
) {

    fun toData(conversion: ConversionDomainModel.Conversion): ConversionDataModel =
        ConversionDataModel(
            fromCurrency = conversion.fromCurrency,
            toCurrency = conversion.toCurrency,
            fromAmount = conversion.fromAmount,
            toAmount = conversion.toAmount,
            commission = commissionToDataMapper.toData(conversion.commission),
            timeStamp = conversion.timeStamp,
        )
}
