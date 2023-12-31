package com.ua.currency.exchanger.exchange.presentation.mapper

import com.ua.currency.exchanger.exchange.domain.model.ConversionDomainModel
import com.ua.currency.exchanger.exchange.presentation.model.ConversionPresentationModel

class ConversionMapper(
    private val commissionMapper: CommissionMapper
) {

    fun toDomain(conversionPresentationModel: ConversionPresentationModel) : ConversionDomainModel.Conversion {
        return ConversionDomainModel.Conversion(
            fromAmount = conversionPresentationModel.fromAmount,
            fromCurrency = conversionPresentationModel.fromCurrency,
            toAmount = conversionPresentationModel.toAmount,
            toCurrency = conversionPresentationModel.toCurrency,
            commission = commissionMapper.toDomain(conversionPresentationModel.commission),
            timeStamp = conversionPresentationModel.timeStamp,
        )
    }

    fun toPresentation(conversionDomainModel: ConversionDomainModel.Conversion) : ConversionPresentationModel {
        return ConversionPresentationModel(
            fromAmount = conversionDomainModel.fromAmount,
            fromCurrency = conversionDomainModel.fromCurrency,
            toAmount = conversionDomainModel.toAmount,
            toCurrency = conversionDomainModel.toCurrency,
            commission = commissionMapper.toPresentation(conversionDomainModel.commission),
            timeStamp = conversionDomainModel.timeStamp,
        )
    }
}
